package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Représente un hexagone régulier dans le jeu
 */
public class HexagonShape extends ShapeModel {
    private static final int DEFAULT_SIZE = 40;
    private int size;

    public HexagonShape(Point position, Color color, int size) {
        super(position, color);
        this.size = size;
    }

    public HexagonShape(Point position, Color color) {
        this(position, color, DEFAULT_SIZE);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        int x = getX();
        int y = getY();

        // Calculer les points de l'hexagone
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = i * Math.PI / 3;
            xPoints[i] = x + size / 2 + (int) (size / 2 * Math.cos(angle));
            yPoints[i] = y + size / 2 + (int) (size / 2 * Math.sin(angle));
        }

        Polygon hexagon = new Polygon(xPoints, yPoints, 6);
        g2d.fillPolygon(hexagon);
    }

    @Override
    public boolean contains(Point p) {
        int x = getX();
        int y = getY();

        // Calculer les points de l'hexagone
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = i * Math.PI / 3;
            xPoints[i] = x + size / 2 + (int) (size / 2 * Math.cos(angle));
            yPoints[i] = y + size / 2 + (int) (size / 2 * Math.sin(angle));
        }

        Polygon hexagon = new Polygon(xPoints, yPoints, 6);
        return hexagon.contains(p);
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
        // Aire d'un hexagone régulier
        return 3 * Math.sqrt(3) * size * size / 4;
    }
}