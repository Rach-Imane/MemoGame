package controller.state;

import controller.GameController;
import command.MoveShapeCommand;
import model.ShapeModel;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * État lorsqu'une forme est en cours de déplacement.
 */
public class MovingShapeState implements InteractionState {
    private ShapeModel selectedShape;
    private Point dragStart;

    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        // Ne fait rien dans cet état
    }

    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
        if (selectedShape != null && dragStart != null) {
            Point newPosition = e.getPoint();
            if (!newPosition.equals(dragStart)) {
                controller.getCommandManager().executeCommand(
                        new MoveShapeCommand(controller.getGameState(), selectedShape, newPosition));
            }
        }
        selectedShape = null;
        dragStart = null;
        controller.setCurrentState(new IdleState());
    }

    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
        if (selectedShape != null) {
            Point newPosition = e.getPoint();
            selectedShape.setPosition(newPosition);
            controller.getGameView().getDrawingPanel().repaint();
        }
    }

    public void setSelectedShape(ShapeModel shape) {
        this.selectedShape = shape;
    }

    public void setDragStart(Point point) {
        this.dragStart = point;
    }
}