package controller.state;

import controller.GameController;
import model.RectangleShape;
import command.AddShapeCommand;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;

/**
 * État pour ajouter un rectangle.
 */
public class AddingRectangleState implements InteractionState {
    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 40;

    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        // Ne rien faire si ce n'est pas le tour du joueur
        if (!controller.getGameState().isPlayerTurn()) {
            return;
        }

        Point p = e.getPoint();
        RectangleShape newShape = new RectangleShape(p, Color.GREEN, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        controller.getCommandManager().executeCommand(
                new AddShapeCommand(controller.getGameState(), newShape));

        controller.setCurrentState(new IdleState());
    }

    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
    }

    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
        // Ne rien faire
    }
}