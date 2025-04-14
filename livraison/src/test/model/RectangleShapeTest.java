package test.model;

import model.RectangleShape;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Point;
import java.awt.Color;

public class RectangleShapeTest {

    @Test
    public void testRectangleCreation() {
        Point position = new Point(100, 100);
        RectangleShape rectangle = new RectangleShape(position, Color.BLUE, 80, 40);

        assertEquals(100, rectangle.getX());
        assertEquals(100, rectangle.getY());
        assertEquals(80, rectangle.getWidth());
        assertEquals(40, rectangle.getHeight());
        assertEquals(Color.BLUE, rectangle.getColor());
    }

    @Test
    public void testContains() {
        Point position = new Point(100, 100);
        RectangleShape rectangle = new RectangleShape(position, Color.BLUE, 80, 40);

        // Point à l'intérieur du rectangle
        assertTrue(rectangle.contains(new Point(120, 120)));

        // Point à l'extérieur du rectangle
        assertFalse(rectangle.contains(new Point(200, 200)));

        // Point sur le bord du rectangle
        assertTrue(rectangle.contains(new Point(180, 100)));
    }

    @Test
    public void testArea() {
        RectangleShape rectangle = new RectangleShape(new Point(0, 0), Color.BLUE, 10, 20);
        assertEquals(200.0, rectangle.getArea(), 0.001);
    }
}