package controller.state;

import controller.GameController;
import model.RectangleShape;
import command.AddShapeCommand;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Point;

/**
 * État pour ajouter un rectangle.
 * <p>
 * Cette classe implémente le patron de conception "État" et représente
 * l'état du contrôleur lorsque l'utilisateur est en train d'ajouter un
 * rectangle
 * au jeu. Quand l'utilisateur clique sur le panneau de dessin, un rectangle
 * est créé à l'emplacement du clic.
 * </p>
 */
public class AddingRectangleState implements InteractionState {
    /** Largeur par défaut des rectangles créés */
    private static final int DEFAULT_WIDTH = 50;

    /** Hauteur par défaut des rectangles créés */
    private static final int DEFAULT_HEIGHT = 40;

    /**
     * Constructeur par défaut.
     * <p>
     * Crée un nouvel état pour l'ajout de rectangles.
     * </p>
     */
    public AddingRectangleState() {
        // Constructeur par défaut
    }

    /**
     * Gère l'événement de pression du bouton de la souris.
     * <p>
     * Crée un nouveau rectangle à la position du clic et l'ajoute au jeu
     * via une commande, puis repasse à l'état Idle.
     * </p>
     * 
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        // Ne rien faire si ce n'est pas le tour du joueur
        if (!controller.getGameState().isPlayerTurn()) {
            return;
        }

        Point p = e.getPoint();
        RectangleShape newShape = new RectangleShape(p, Color.GREEN, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        controller.getCommandManager().executeCommand(
                new AddShapeCommand(controller.getGameState(), newShape));

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
        // Ne rien faire
    }
}