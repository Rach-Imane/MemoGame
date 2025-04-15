package controller.state;

import controller.GameController;
import java.awt.event.MouseEvent;

/**
 * Interface pour le pattern State définissant le comportement
 * en fonction de l'état d'interaction de la souris.
 * <p>
 * Cette interface fait partie du patron de conception "État" (State Pattern)
 * qui permet de changer le comportement d'un objet quand son état interne
 * change.
 * Chaque état d'interaction (sélection, déplacement, ajout de forme, etc.)
 * implémente cette interface pour définir comment les événements de souris
 * doivent être traités selon l'état actuel du contrôleur.
 * </p>
 */
public interface InteractionState {

    /**
     * Gère l'événement de pression du bouton de la souris.
     * <p>
     * La mise en œuvre de cette méthode dépend de l'état concret.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    void handleMousePressed(MouseEvent e, GameController controller);

    /**
     * Gère l'événement de relâchement du bouton de la souris.
     * <p>
     * La mise en œuvre de cette méthode dépend de l'état concret.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    void handleMouseReleased(MouseEvent e, GameController controller);

    /**
     * Gère l'événement de glissement de la souris.
     * <p>
     * La mise en œuvre de cette méthode dépend de l'état concret.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    void handleMouseDragged(MouseEvent e, GameController controller);

}