package evaluation;

import model.ShapeModel;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class ColorWeightedScore implements ScoreStrategy {
    private static final double POSITION_TOLERANCE = 50.0;

    @Override
    public int calculateScore(List<ShapeModel> modelShapes, List<ShapeModel> playerShapes) {
        if (modelShapes == null || playerShapes == null || modelShapes.isEmpty()) {
            return 0;
        }

        double colorScore = 0.0;
        double typeScore = 0.0;
        double positionScore = 0.0;

        List<ShapeModel> availablePlayerShapes = new ArrayList<>(playerShapes);

        // Pour chaque forme du modèle
        for (ShapeModel modelShape : modelShapes) {
            ShapeModel bestMatch = findBestMatch(modelShape, availablePlayerShapes);

            if (bestMatch != null) {
                // Couleur (40 points)
                if (modelShape.getColor().equals(bestMatch.getColor())) {
                    colorScore += 40.0 / modelShapes.size();
                }

                // Type de forme (30 points)
                if (modelShape.getClass().equals(bestMatch.getClass())) {
                    typeScore += 30.0 / modelShapes.size();
                }

                // Position (30 points)
                double distance = modelShape.getPosition().distance(bestMatch.getPosition());
                double positionAccuracy = Math.max(0, 1 - (distance / POSITION_TOLERANCE));
                positionScore += (30.0 * positionAccuracy) / modelShapes.size();

                availablePlayerShapes.remove(bestMatch);
            }
        }

        // Pénalité pour les formes en trop
        double penalty = Math.max(0, playerShapes.size() - modelShapes.size()) * 5.0;

        // Score final sur 100
        int finalScore = (int) Math.max(0, Math.min(100,
                colorScore + typeScore + positionScore - penalty));

        System.out.println("[Score] Détail du score (accent sur les couleurs) :");
        System.out.println("  - Couleur : " + String.format("%.1f", colorScore) + "/40");
        System.out.println("  - Type : " + String.format("%.1f", typeScore) + "/30");
        System.out.println("  - Position : " + String.format("%.1f", positionScore) + "/30");
        System.out.println("  - Pénalité : -" + String.format("%.1f", penalty));
        System.out.println("  - Score final : " + finalScore + "/100");

        return finalScore;
    }

    private ShapeModel findBestMatch(ShapeModel modelShape, List<ShapeModel> availableShapes) {
        ShapeModel bestMatch = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (ShapeModel playerShape : availableShapes) {
            if (modelShape.getClass().equals(playerShape.getClass())) {
                double distance = modelShape.getPosition().distance(playerShape.getPosition());
                if (distance < minDistance && distance <= POSITION_TOLERANCE) {
                    minDistance = distance;
                    bestMatch = playerShape;
                }
            }
        }

        return bestMatch;
    }
}