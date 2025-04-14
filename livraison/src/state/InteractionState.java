package state;

import java.awt.event.MouseEvent;
import model.ShapeModel;

public interface InteractionState {
    void mousePressed(MouseEvent e);

    void mouseReleased(MouseEvent e);

    void mouseDragged(MouseEvent e);

    void mouseClicked(MouseEvent e);

    ShapeModel getCurrentShape();
}