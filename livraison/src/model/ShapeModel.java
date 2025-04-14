package model;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * Classe abstraite représentant une forme géométrique dans le jeu
 */
public abstract class ShapeModel implements Serializable {
    protected Point position;
    protected Color color;

    public ShapeModel(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return position.x;
    }

    public void setX(int x) {
        position.x = x;
    }

    public int getY() {
        return position.y;
    }

    public void setY(int y) {
        position.y = y;
    }

    // Méthodes abstraites pour les dimensions
    public abstract int getWidth();

    public abstract int getHeight();

    public abstract boolean contains(Point p);

    public abstract double getArea();

    public abstract void draw(java.awt.Graphics g);

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ShapeModel other = (ShapeModel) obj;
        return position.equals(other.position) && color.equals(other.color);
    }

    @Override
    public int hashCode() {
        return 31 * position.hashCode() + color.hashCode();
    }
}