package evaluation;

import model.ShapeModel;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class StrictScore implements ScoreStrategy {
    private static final double POSITION_TOLERANCE = 5.0; // Très strict sur la position

    @Override
    public int calculateScore(List<ShapeModel> modelShapes, List<ShapeModel> playerShapes) {
        if (modelShapes == null || playerShapes == null || modelShapes.isEmpty()) {
            return 0;
        }

        // Vérifier d'abord le nombre de formes
        if (modelShapes.size() != playerShapes.size()) {
            return 0;
        }

        List<ShapeModel> availablePlayerShapes = new ArrayList<>(playerShapes);
        int perfectMatches = 0;

        // Pour chaque forme du modèle
        for (ShapeModel modelShape : modelShapes) {
            ShapeModel bestMatch = findBestMatch(modelShape, availablePlayerShapes);

            if (bestMatch != null) {
                // Vérifier la correspondance exacte
                if (isExactMatch(modelShape, bestMatch)) {
                    perfectMatches++;
                }
                availablePlayerShapes.remove(bestMatch);
            }
        }

        // Score final : 100 si toutes les formes correspondent parfaitement, 0 sinon
        int finalScore = (perfectMatches == modelShapes.size()) ? 100 : 0;

        System.out.println("[Score] Détail du score strict :");
        System.out.println("  - Correspondances parfaites : " + perfectMatches + "/" + modelShapes.size());
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

    private boolean isExactMatch(ShapeModel model, ShapeModel player) {
        return model.getClass().equals(player.getClass()) &&
                model.getPosition().distance(player.getPosition()) <= POSITION_TOLERANCE &&
                model.getColor().equals(player.getColor());
    }
}