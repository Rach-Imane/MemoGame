package evaluation;

import model.ShapeModel;
import java.util.List;

/**
 * Interface définissant la stratégie d'évaluation du score
 */
public interface ScoreStrategy {

    int calculateScore(List<ShapeModel> modelShapes, List<ShapeModel> playerShapes);
}