package strategy;

import model.ShapeModel;
import model.RectangleShape;
import model.CircleShape;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Stratégie de génération de formes à partir d'un fichier
 */
public class FileShapeStrategy implements ShapeGenerationStrategy {
    private String filename;
    
    public FileShapeStrategy(String filename) {
        this.filename = filename;
    }
    
    @Override
    public List<ShapeModel> generateShapes() {
        List<ShapeModel> shapes = new ArrayList<>();
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            shapes = (List<ShapeModel>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return shapes;
    }
    
    @Override
    public void saveShapes(List<ShapeModel> shapes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(shapes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 