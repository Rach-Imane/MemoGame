package state;

import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Color;
import model.CircleShape;
import model.ShapeModel;

public class CircleCreationState implements InteractionState {
    private CircleShape currentShape;
    private int startX, startY;

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
        currentShape = new CircleShape(new Point(startX, startY), Color.RED, 0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int radius = (int) Math.sqrt(Math.pow(e.getX() - startX, 2) + Math.pow(e.getY() - startY, 2));
        currentShape.setRadius(radius);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int radius = (int) Math.sqrt(Math.pow(e.getX() - startX, 2) + Math.pow(e.getY() - startY, 2));
        currentShape.setRadius(radius);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Non utilisé pour la création de cercle
    }

    @Override
    public ShapeModel getCurrentShape() {
        return currentShape;
    }
}