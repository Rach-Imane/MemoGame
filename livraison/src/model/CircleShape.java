package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/**
 * Classe représentant un cercle dans le jeu
 */
public class CircleShape extends ShapeModel {
    private int radius;

    public CircleShape(Point position, Color color, int radius) {
        super(position, color);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return radius * 2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(position.x - radius, position.y - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean contains(Point p) {
        // Calculer la distance entre le point p et le centre du cercle
        double distance = Math.sqrt(
                Math.pow(p.x - position.x, 2) +
                        Math.pow(p.y - position.y, 2));

        // Vérifier si la distance est inférieure ou égale au rayon
        // Ajouter une marge de 5 pixels pour faciliter la sélection
        return distance <= radius + 5;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}