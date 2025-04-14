package test.state;

import state.CircleCreationState;
import model.CircleShape;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Point;

public class CircleCreationStateTest {

    private MouseEvent createMouseEvent(int x, int y, int id) {
        return new MouseEvent(new Component() {
        }, id, System.currentTimeMillis(), 0, x, y, 1, false);
    }

    @Test
    public void testCircleCreation() {
        CircleCreationState state = new CircleCreationState();

        // Simuler un clic de souris
        MouseEvent pressEvent = createMouseEvent(100, 100, MouseEvent.MOUSE_PRESSED);
        state.mousePressed(pressEvent);

        // Vérifier que la forme est créée à la bonne position
        CircleShape shape = (CircleShape) state.getCurrentShape();
        assertNotNull(shape);
        assertEquals(100, shape.getX());
        assertEquals(100, shape.getY());
        assertEquals(0, shape.getRadius());

        // Simuler un glissement de souris
        MouseEvent dragEvent = createMouseEvent(150, 150, MouseEvent.MOUSE_DRAGGED);
        state.mouseDragged(dragEvent);

        // Vérifier que le rayon est mis à jour
        double expectedRadius = Math.sqrt(2 * 50 * 50);
        assertEquals(expectedRadius, shape.getRadius(), 1);
    }
}