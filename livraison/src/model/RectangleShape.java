package model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point;

/**
 * Classe représentant un rectangle dans le jeu
 */
public class RectangleShape extends ShapeModel {
    private int width;
    private int height;

    public RectangleShape(Point position, Color color, int width, int height) {
        super(position, color);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(position.x, position.y, width, height);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= position.x - 5 && p.x <= position.x + width + 5 &&
                p.y >= position.y - 5 && p.y <= position.y + height + 5;
    }

    @Override
    public double getArea() {
        return width * height;
    }
}