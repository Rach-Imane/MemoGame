package test.model;

import model.CircleShape;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Point;
import java.awt.Color;

public class CircleShapeTest {

    @Test
    public void testCircleCreation() {
        Point position = new Point(100, 100);
        CircleShape circle = new CircleShape(position, Color.RED, 50);

        assertEquals(100, circle.getX());
        assertEquals(100, circle.getY());
        assertEquals(50, circle.getRadius());
        assertEquals(Color.RED, circle.getColor());
    }

    @Test
    public void testContains() {
        Point center = new Point(100, 100);
        CircleShape circle = new CircleShape(center, Color.RED, 50);

        // Point à l'intérieur du cercle
        assertTrue(circle.contains(new Point(120, 120)));

        // Point à l'extérieur du cercle
        assertFalse(circle.contains(new Point(200, 200)));

        // Point sur le bord du cercle
        assertTrue(circle.contains(new Point(150, 100)));
    }

    @Test
    public void testArea() {
        CircleShape circle = new CircleShape(new Point(0, 0), Color.RED, 10);
        double expectedArea = Math.PI * 100; // r² = 10²
        assertEquals(expectedArea, circle.getArea(), 0.001);
    }
}