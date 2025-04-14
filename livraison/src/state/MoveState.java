package state;

import java.awt.event.MouseEvent;
import model.ShapeModel;
import java.util.List;

public class MoveState implements InteractionState {
    private ShapeModel selectedShape;
    private int offsetX, offsetY;

    @Override
    public void mousePressed(MouseEvent e) {
        // À implémenter dans le contexte du jeu
        // Trouver la forme sous le curseur
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        selectedShape = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedShape != null) {
            selectedShape.setX(e.getX() - offsetX);
            selectedShape.setY(e.getY() - offsetY);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Non utilisé pour le déplacement
    }

    @Override
    public ShapeModel getCurrentShape() {
        return selectedShape;
    }
}