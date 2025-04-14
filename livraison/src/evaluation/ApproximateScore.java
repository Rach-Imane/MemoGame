package evaluation;

import model.ShapeModel;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

/**
 * Stratégie d'évaluation :
 * - 50 points pour la correspondance des types de formes
 * - 40 points pour le positionnement
 * - 10 points pour la correspondance des couleurs
 */
public class ApproximateScore implements ScoreStrategy {
    private static final double POSITION_TOLERANCE = 150.0;

    @Override
    public int calculateScore(List<ShapeModel> modelShapes, List<ShapeModel> playerShapes) {
        if (modelShapes == null || playerShapes == null || modelShapes.isEmpty()) {
            return 0;
        }

        double typeScore = 0.0;
        double positionScore = 0.0;
        double colorScore = 0.0;

        List<ShapeModel> availablePlayerShapes = new ArrayList<>(playerShapes);

        // Pour chaque forme du modèle
        for (ShapeModel modelShape : modelShapes) {
            ShapeModel bestMatch = findBestMatch(modelShape, availablePlayerShapes);

            if (bestMatch != null) {
                // Type de forme (50 points)
                if (modelShape.getClass().equals(bestMatch.getClass())) {
                    typeScore += 50.0 / modelShapes.size();
                }

                // Position (40 points)
                double distance = modelShape.getPosition().distance(bestMatch.getPosition());
                double positionAccuracy = Math.max(0, 1 - (distance / POSITION_TOLERANCE));
                positionScore += (40.0 * positionAccuracy) / modelShapes.size();

                // Couleur (10 points)
                if (modelShape.getColor().equals(bestMatch.getColor())) {
                    colorScore += 10.0 / modelShapes.size();
                }

                availablePlayerShapes.remove(bestMatch);
            }
        }

        // Pénalité pour les formes en trop
        double penalty = Math.max(0, playerShapes.size() - modelShapes.size()) * 2.0;

        // Score final sur 100
        int finalScore = (int) Math.max(0, Math.min(100,
                typeScore + positionScore + colorScore - penalty));

        System.out.println("[Score] Détail du score :");
        System.out.println("  - Type : " + String.format("%.1f", typeScore) + "/50");
        System.out.println("  - Position : " + String.format("%.1f", positionScore) + "/40");
        System.out.println("  - Couleur : " + String.format("%.1f", colorScore) + "/10");
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