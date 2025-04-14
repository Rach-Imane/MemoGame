package controller.state;

import controller.GameController;
import model.ShapeModel;
import java.awt.event.MouseEvent;
import java.awt.Point;

/**
 * État par défaut: attend une sélection pour déplacer une forme.
 */
public class IdleState implements InteractionState {

    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        if (!controller.getGameState().isPlayerTurn()) {
            return;
        }

        Point p = e.getPoint();
        ShapeModel shape = controller.findShapeAt(p);

        if (shape != null) {
            controller.setSelectedShape(shape);
            controller.setDragStart(p);
            controller.setCurrentState(new MovingShapeState());
        }
    }

    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
    }

    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
    }
}