package controller.state;

import controller.GameController;
import model.CircleShape;
import command.AddShapeCommand;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;

/**
 * État lorsqu'on s'apprête à ajouter un cercle.
 * <p>
 * Cette classe implémente le patron de conception "État" et représente
 * l'état du contrôleur lorsque l'utilisateur est en train d'ajouter un cercle
 * au jeu. Quand l'utilisateur clique sur le panneau de dessin, un cercle
 * est créé à l'emplacement du clic.
 * </p>
 */
public class AddingCircleState implements InteractionState {
    /** Rayon par défaut des cercles créés */
    private static final int DEFAULT_RADIUS = 30;

    /**
     * Constructeur par défaut.
     * <p>
     * Crée un nouvel état pour l'ajout de cercles.
     * </p>
     */
    public AddingCircleState() {
        // Constructeur par défaut
    }

    /**
     * Gère l'événement de pression du bouton de la souris.
     * <p>
     * Crée un nouveau cercle à la position du clic et l'ajoute au jeu
     * via une commande, puis repasse à l'état Idle.
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
        CircleShape circle = new CircleShape(p, controller.getSelectedColor(), DEFAULT_RADIUS);
        controller.getCommandManager().executeCommand(
                new AddShapeCommand(controller.getGameState(), circle));

        controller.setCurrentState(new IdleState());
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
        // Ne fait rien dans cet état
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
        // Ne fait rien dans cet état
    }
}