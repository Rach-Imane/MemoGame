package controller.state;

import controller.GameController;
import java.awt.event.MouseEvent;

/**
 * Interface pour le pattern State définissant le comportement
 * en fonction de l'état d'interaction de la souris.
 */
public interface InteractionState {

    void handleMousePressed(MouseEvent e, GameController controller);

    void handleMouseReleased(MouseEvent e, GameController controller);

    void handleMouseDragged(MouseEvent e, GameController controller);

}