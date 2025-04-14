package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Point;

/**
 * Classe représentant un ensemble de formes qui peut être sérialisé
 */
public class ShapeSet implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<ShapeModel> shapes;
    private int difficulty; // 1-5 pour indiquer la difficulté

    public ShapeSet(String name, int difficulty) {
        this.name = name;
        this.shapes = new ArrayList<>();
        this.difficulty = difficulty;
    }

    public ShapeSet(String name, List<ShapeModel> shapes, int difficulty) {
        this.name = name;
        this.shapes = new ArrayList<>(shapes);
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShapeModel> getShapes() {
        return new ArrayList<>(shapes);
    }

    public void setShapes(List<ShapeModel> shapes) {
        this.shapes = new ArrayList<>(shapes);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = Math.max(1, Math.min(5, difficulty));
    }

    public void addShape(ShapeModel shape) {
        shapes.add(shape);
    }

    public void removeShape(ShapeModel shape) {
        shapes.remove(shape);
    }

    public int getShapeCount() {
        return shapes.size();
    }

    @Override
    public String toString() {
        return name + " (" + shapes.size() + " formes, difficulté: " + difficulty + ")";
    }
}