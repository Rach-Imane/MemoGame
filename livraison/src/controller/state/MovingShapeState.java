package controller.state;

import controller.GameController;
import command.MoveShapeCommand;
import model.ShapeModel;

import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * État lorsqu'une forme est en cours de déplacement.
 * <p>
 * Cette classe implémente le patron de conception "État" et représente
 * l'état du contrôleur lorsque l'utilisateur déplace une forme.
 * Elle gère le comportement de glisser-déposer (drag and drop) des formes.
 * </p>
 */
public class MovingShapeState implements InteractionState {
    /** La forme sélectionnée en cours de déplacement */
    private ShapeModel selectedShape;

    /** Le point de départ du glissement */
    private Point dragStart;

    /**
     * Constructeur par défaut.
     * <p>
     * Crée un état de déplacement sans forme ni point de départ.
     * Ces valeurs devront être définies ultérieurement.
     * </p>
     */
    public MovingShapeState() {
        // Constructeur vide par défaut
    }

    /**
     * Constructeur avec paramètres.
     * <p>
     * Crée un état de déplacement avec une forme et un point de départ prédéfinis.
     * </p>
     * 
     * @param shape La forme à déplacer
     * @param point Le point de départ du glissement
     */
    public MovingShapeState(ShapeModel shape, Point point) {
        this.selectedShape = shape;
        this.dragStart = point;
    }

    /**
     * Gère l'événement de pression du bouton de la souris.
     * <p>
     * Cette méthode ne fait rien dans cet état car la sélection
     * a déjà été effectuée.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMousePressed(MouseEvent e, GameController controller) {
        // Ne fait rien dans cet état
    }

    /**
     * Gère l'événement de relâchement du bouton de la souris.
     * <p>
     * Termine le déplacement de la forme en créant une commande MoveShapeCommand
     * pour enregistrer le mouvement, puis repasse à l'état Idle.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMouseReleased(MouseEvent e, GameController controller) {
        if (selectedShape != null && dragStart != null) {
            Point newPosition = e.getPoint();
            if (!newPosition.equals(dragStart)) {
                controller.getCommandManager().executeCommand(
                        new MoveShapeCommand(controller.getGameState(), selectedShape, newPosition));
            }
        }
        selectedShape = null;
        dragStart = null;
        controller.setCurrentState(new IdleState());
    }

    /**
     * Gère l'événement de glissement de la souris.
     * <p>
     * Met à jour la position de la forme sélectionnée pour suivre le curseur
     * et rafraîchit l'affichage.
     * </p>
     *
     * @param e          Le MouseEvent contenant les informations sur l'événement
     *                   souris
     * @param controller Le contrôleur du jeu qui gère l'état courant
     */
    @Override
    public void handleMouseDragged(MouseEvent e, GameController controller) {
        if (selectedShape != null) {
            Point newPosition = e.getPoint();
            selectedShape.setPosition(newPosition);
            controller.getGameView().getDrawingPanel().repaint();
        }
    }

    /**
     * Définit la forme sélectionnée à déplacer.
     *
     * @param shape La forme à déplacer
     */
    public void setSelectedShape(ShapeModel shape) {
        this.selectedShape = shape;
    }

    /**
     * Définit le point de départ du glissement.
     *
     * @param point Le point où le glissement a commencé
     */
    public void setDragStart(Point point) {
        this.dragStart = point;
    }
}