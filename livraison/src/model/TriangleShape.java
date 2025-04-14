package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Représente un triangle dans le jeu
 */
public class TriangleShape extends ShapeModel {
    private static final int DEFAULT_SIZE = 40;
    private int size;

    public TriangleShape(Point position, Color color, int size) {
        super(position, color);
        this.size = size;
    }

    public TriangleShape(Point position, Color color) {
        this(position, color, DEFAULT_SIZE);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        // Calculer les trois points du triangle
        int x = getX();
        int y = getY();

        // Triangle équilatéral
        int[] xPoints = { x, x + size, x + size / 2 };
        int[] yPoints = { y + size, y + size, y };

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        g2d.fillPolygon(triangle);
    }

    @Override
    public boolean contains(Point p) {
        int x = getX();
        int y = getY();

        // Points du triangle
        int[] xPoints = { x, x + size, x + size / 2 };
        int[] yPoints = { y + size, y + size, y };

        Polygon triangle = new Polygon(xPoints, yPoints, 3);
        return triangle.contains(p);
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public double getArea() {
        // Aire d'un triangle équilatéral
        return Math.sqrt(3) * size * size / 4;
    }
}