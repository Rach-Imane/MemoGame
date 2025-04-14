package command;

import model.ShapeModel;
import model.GameState;
import java.awt.Point;

/**
 * Commande pour déplacer une forme
 */
public class MoveShapeCommand implements Command {
    private GameState gameState;
    private ShapeModel shape;
    private Point oldPosition;
    private Point newPosition;

    public MoveShapeCommand(GameState gameState, ShapeModel shape, Point newPosition) {
        this.gameState = gameState;
        this.shape = shape;
        this.oldPosition = shape.getPosition();
        this.newPosition = newPosition;
    }

    @Override
    public void execute() {
        shape.setPosition(newPosition);
        gameState.notifyObservers();
    }

    @Override
    public void undo() {
        shape.setPosition(oldPosition);
        gameState.notifyObservers();
    }
}