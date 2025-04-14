package command;

import model.ShapeModel;
import model.GameState;

/**
 * Commande pour ajouter une forme au jeu
 */
public class AddShapeCommand implements Command {
    private GameState gameState;
    private ShapeModel shape;

    public AddShapeCommand(GameState gameState, ShapeModel shape) {
        this.gameState = gameState;
        this.shape = shape;
    }

    @Override
    public void execute() {
        // Vérifier si la forme existe déjà dans la liste
        if (!gameState.getShapes().contains(shape)) {
            gameState.addShape(shape);
        }
    }

    @Override
    public void undo() {
        // Vérifier si la forme existe dans la liste avant de la supprimer
        if (gameState.getShapes().contains(shape)) {
            gameState.removeShape(shape);
        }
    }
}