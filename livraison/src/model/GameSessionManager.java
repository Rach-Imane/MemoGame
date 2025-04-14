package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Gestionnaire des sessions de jeu
 */
public class GameSessionManager {
    private static final String SAVE_DIRECTORY = "saved_sessions";
    private static final String SAVE_FILE = "sessions.dat";
    private static final Logger LOGGER = Logger.getLogger(GameSessionManager.class.getName());

    public GameSessionManager() {
        File directory = new File(SAVE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void saveSession(GameSession session) {
        File file = new File(SAVE_DIRECTORY, SAVE_FILE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(session);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde de la session", e);
        }
    }

    public GameSession loadSession() {
        File file = new File(SAVE_DIRECTORY, SAVE_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (GameSession) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de la session", e);
            return null;
        }
    }

    public void saveShapeSet(ShapeSet shapeSet) {
        File file = new File(SAVE_DIRECTORY, "shapeset_" + shapeSet.getName() + ".dat");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(shapeSet);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la sauvegarde de l'ensemble de formes", e);
        }
    }

    public ShapeSet loadShapeSet(String name) {
        File file = new File(SAVE_DIRECTORY, "shapeset_" + name + ".dat");
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ShapeSet) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors du chargement de l'ensemble de formes", e);
            return null;
        }
    }

    public List<ShapeSet> loadAllShapeSets() {
        List<ShapeSet> shapeSets = new ArrayList<>();
        File directory = new File(SAVE_DIRECTORY);
        File[] files = directory.listFiles((dir, name) -> name.startsWith("shapeset_") && name.endsWith(".dat"));

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    ShapeSet shapeSet = (ShapeSet) ois.readObject();
                    shapeSets.add(shapeSet);
                } catch (IOException | ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, "Erreur lors du chargement de l'ensemble de formes " + file.getName(), e);
                }
            }
        }

        return shapeSets;
    }
}