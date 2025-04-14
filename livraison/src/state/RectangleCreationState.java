package state;

import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;
import model.RectangleShape;
import model.ShapeModel;

public class RectangleCreationState implements InteractionState {
    private RectangleShape currentShape;
    private int startX, startY;

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        currentShape = new RectangleShape(new Point(startX, startY), Color.BLUE, 0, 0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int width = Math.abs(e.getX() - startX);
        int height = Math.abs(e.getY() - startY);
        currentShape.setWidth(width);
        currentShape.setHeight(height);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int width = Math.abs(e.getX() - startX);
        int height = Math.abs(e.getY() - startY);
        currentShape.setWidth(width);
        currentShape.setHeight(height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Non utilisé pour la création de rectangle
    }

    @Override
    public ShapeModel getCurrentShape() {
        return currentShape;
    }
}