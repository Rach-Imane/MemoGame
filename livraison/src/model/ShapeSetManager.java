package model;

import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Gestionnaire des ensembles de formes
 */
public class ShapeSetManager {
    private static final String SAVE_DIRECTORY = "saved_shapes";
    private static final String SAVE_FILE = "shapesets.dat";
    private static final Logger LOGGER = Logger.getLogger(ShapeSetManager.class.getName());
    private List<ShapeSet> shapeSets;
    private Random random;

    public ShapeSetManager() {
        this.shapeSets = new ArrayList<>();
        this.random = new Random();
        loadShapeSets();
    }

    /**
     * Charge les ensembles de formes depuis le fichier
     */
    private void loadShapeSets() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, SAVE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                shapeSets = (List<ShapeSet>) ois.readObject();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Erreur lors du chargement des ensembles de formes", e);
                shapeSets = new ArrayList<>();
            }
        }
    }

    /**
     * Sauvegarde les ensembles de formes dans un fichier
     */
    public void saveShapeSets() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(directory, SAVE_FILE)))) {
            oos.writeObject(shapeSets);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde des ensembles de formes", e);
        }
    }

    /**
     * Ajoute un nouvel ensemble de formes
     */
    public void addShapeSet(ShapeSet shapeSet) {
        shapeSets.add(shapeSet);
        saveShapeSets();
    }

    /**
     * Supprime un ensemble de formes
     */
    public void removeShapeSet(ShapeSet shapeSet) {
        shapeSets.remove(shapeSet);
        saveShapeSets();
    }

    /**
     * Récupère tous les ensembles de formes
     */
    public List<ShapeSet> getAllShapeSets() {
        return new ArrayList<>(shapeSets);
    }

    /**
     * Génère un ensemble de formes aléatoire
     */
    public ShapeSet generateRandomShapeSet(int shapeCount) {
        List<ShapeModel> shapes = new ArrayList<>();
        String[] shapeTypes = { "Circle", "Rectangle", "Triangle", "Star", "Hexagon" };
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE };

        for (int i = 0; i < shapeCount; i++) {
            String shapeType = shapeTypes[random.nextInt(shapeTypes.length)];
            Color color = colors[random.nextInt(colors.length)];
            Point position = new Point(
                    random.nextInt(700) + 50, // x entre 50 et 750
                    random.nextInt(500) + 50 // y entre 50 et 550
            );

            ShapeModel shape = null;
            switch (shapeType) {
                case "Circle":
                    shape = new CircleShape(position, color, 30);
                    break;
                case "Rectangle":
                    shape = new RectangleShape(position, color, 60, 40);
                    break;
                case "Triangle":
                    shape = new TriangleShape(position, color, 40);
                    break;
                case "Star":
                    shape = new StarShape(position, color, 30);
                    break;
                case "Hexagon":
                    shape = new HexagonShape(position, color, 30);
                    break;
            }

            if (shape != null) {
                shapes.add(shape);
            }
        }

        return new ShapeSet("Ensemble aléatoire " + (shapeSets.size() + 1), shapes, random.nextInt(5) + 1);
    }

    /**
     * Récupère un ensemble de formes aléatoire
     */
    public ShapeSet getRandomShapeSet() {
        if (shapeSets.isEmpty()) {
            return generateRandomShapeSet(4);
        }
        return shapeSets.get(random.nextInt(shapeSets.size()));
    }
}