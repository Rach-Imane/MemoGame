package controller.state;

import controller.GameController;
import model.ShapeModel;
import java.awt.event.MouseEvent;
import java.awt.Point;

/**
 * État par défaut: attend une sélection pour déplacer une forme.
 * <p>
 * Cette classe implémente le patron de conception "État" et représente
 * l'état initial du contrôleur. Dans cet état, le système attend que
 * l'utilisateur
 * sélectionne une forme pour la déplacer. Lorsqu'une forme est sélectionnée,
 * l'état change vers MovingShapeState.
 * </p>
 */
public class IdleState implements InteractionState {

    /**
     * Constructeur par défaut.
     * <p>
     * Crée un nouvel état d'attente (idle).
     * </p>
     */
    public IdleState() {
        // Constructeur par défaut
    }

    /**
     * Gère l'événement de pression du bouton de la souris.
     * <p>
     * Vérifie si une forme est présente à la position du clic.
     * Si c'est le cas, sélectionne la forme et passe à l'état MovingShapeState.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        if (!controller.getGameState().isPlayerTurn()) {
            return;
        }

        Point p = e.getPoint();
        ShapeModel shape = controller.findShapeAt(p);

        if (shape != null) {
            controller.setSelectedShape(shape);
            controller.setDragStart(p);
            MovingShapeState movingState = new MovingShapeState(shape, p);
            controller.setCurrentState(movingState);
        }
    }

    /**
     * Gère l'événement de relâchement du bouton de la souris.
     * <p>
     * Cette méthode ne fait rien dans cet état.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
    }

    /**
     * Gère l'événement de glissement de la souris.
     * <p>
     * Cette méthode ne fait rien dans cet état.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
    }
}