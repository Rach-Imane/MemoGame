package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Représente une étoile à 5 branches dans le jeu
 */
public class StarShape extends ShapeModel {
    private static final int DEFAULT_SIZE = 40;
    private int size;

    public StarShape(Point position, Color color, int size) {
        super(position, color);
        this.size = size;
    }

    public StarShape(Point position, Color color) {
        this(position, color, DEFAULT_SIZE);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(getColor());

        int x = getX();
        int y = getY();

        // Calculer les points de l'étoile
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];

        // Points externes
        for (int i = 0; i < 5; i++) {
            double angle = i * 4 * Math.PI / 5 - Math.PI / 2;
            xPoints[i] = x + size / 2 + (int) (size / 2 * Math.cos(angle));
            yPoints[i] = y + size / 2 + (int) (size / 2 * Math.sin(angle));
        }

        // Points internes
        for (int i = 0; i < 5; i++) {
            double angle = i * 4 * Math.PI / 5 - Math.PI / 2 + Math.PI / 5;
            xPoints[i + 5] = x + size / 2 + (int) (size / 4 * Math.cos(angle));
            yPoints[i + 5] = y + size / 2 + (int) (size / 4 * Math.sin(angle));
        }

        Polygon star = new Polygon(xPoints, yPoints, 10);
        g2d.fillPolygon(star);
    }

    @Override
    public boolean contains(Point p) {
        int x = getX();
        int y = getY();

        // Calculer les points de l'étoile
        int[] xPoints = new int[10];
        int[] yPoints = new int[10];

        // Points externes
        for (int i = 0; i < 5; i++) {
            double angle = i * 4 * Math.PI / 5 - Math.PI / 2;
            xPoints[i] = x + size / 2 + (int) (size / 2 * Math.cos(angle));
            yPoints[i] = y + size / 2 + (int) (size / 2 * Math.sin(angle));
        }

        // Points internes
        for (int i = 0; i < 5; i++) {
            double angle = i * 4 * Math.PI / 5 - Math.PI / 2 + Math.PI / 5;
            xPoints[i + 5] = x + size / 2 + (int) (size / 4 * Math.cos(angle));
            yPoints[i + 5] = y + size / 2 + (int) (size / 4 * Math.sin(angle));
        }

        Polygon star = new Polygon(xPoints, yPoints, 10);
        return star.contains(p);
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
        // Approximation de l'aire d'une étoile à 5 branches
        return Math.PI * size * size / 4;
    }
}