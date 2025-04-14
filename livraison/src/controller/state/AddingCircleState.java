package controller.state;

import controller.GameController;
import model.CircleShape;
import command.AddShapeCommand;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;

/**
 * État lorsqu'on s'apprête à ajouter un cercle.
 */
public class AddingCircleState implements InteractionState {
    private static final int DEFAULT_RADIUS = 30;

    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        if (!controller.getGameState().isPlayerTurn()) {
            return;
        }

        Point p = e.getPoint();
        CircleShape circle = new CircleShape(p.x, p.y, DEFAULT_RADIUS, controller.getCurrentColor());
        controller.getCommandManager().executeCommand(
                new AddShapeCommand(controller.getGameState(), circle));

        controller.setCurrentState(new IdleState());
    }

    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
        // Ne fait rien dans cet état
    }

    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
        // Ne fait rien dans cet état
    }
}