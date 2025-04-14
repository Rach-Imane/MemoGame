package evaluation;

import model.ShapeModel;
import java.util.List;

/**
 * Stratégie d'évaluation basée sur la correspondance exacte des formes
 */
public class ExactMatchScore implements ScoreStrategy {
    @Override
    public int calculateScore(List<ShapeModel> modelShapes, List<ShapeModel> playerShapes) {
        if (modelShapes.isEmpty() && playerShapes.isEmpty()) {
            return 100;
        }
        
        if (modelShapes.isEmpty() || playerShapes.isEmpty()) {
            return 0;
        }
        
        if (modelShapes.size() != playerShapes.size()) {
            return 0;
        }
        
        int totalScore = 0;
        for (int i = 0; i < modelShapes.size(); i++) {
            ShapeModel modelShape = modelShapes.get(i);
            ShapeModel playerShape = playerShapes.get(i);
            
            if (modelShape.getClass().equals(playerShape.getClass())) {
                if (isExactMatch(modelShape, playerShape)) {
                    totalScore += 100;
                }
            }
        }
        
        return totalScore / modelShapes.size();
    }
    
    private boolean isExactMatch(ShapeModel model, ShapeModel player) {
        return model.getPosition().equals(player.getPosition()) &&
               model.getWidth() == player.getWidth() &&
               model.getHeight() == player.getHeight() &&
               model.getColor().equals(player.getColor());
    }
} 