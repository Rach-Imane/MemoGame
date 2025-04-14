package strategy;

import model.ShapeModel;
import model.RectangleShape;
import model.CircleShape;
import model.TriangleShape;
import model.StarShape;
import model.HexagonShape;
import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stratégie de génération aléatoire de formes
 */
public class RandomShapeStrategy implements ShapeGenerationStrategy {
    private Random random = new Random();
    private static final Color[] AVAILABLE_COLORS = {
            new Color(255, 0, 0), // Rouge
            new Color(0, 255, 0), // Vert
            new Color(0, 0, 255) // Bleu
    };
    // Tailles standard
    private static final int STD_RADIUS = 30;
    private static final int STD_RECT_WIDTH = 50;
    private static final int STD_RECT_HEIGHT = 40;
    private static final int STD_SIZE = 40;
    private static final int MAX_SHAPES = 5;

    @Override
    public List<ShapeModel> generateShapes() {
        List<ShapeModel> shapes = new ArrayList<>();
        int numShapes = random.nextInt(MAX_SHAPES) + 1;

        for (int i = 0; i < numShapes; i++) {
            shapes.add(createRandomShape());
        }

        return shapes;
    }

    private ShapeModel createRandomShape() {
        int x = random.nextInt(800 - STD_SIZE);
        int y = random.nextInt(600 - STD_SIZE);
        Color color = AVAILABLE_COLORS[random.nextInt(AVAILABLE_COLORS.length)];

        switch (random.nextInt(5)) {
            case 0:
                return new CircleShape(new Point(x, y), color, STD_RADIUS);
            case 1:
                return new RectangleShape(new Point(x, y), color, STD_RECT_WIDTH, STD_RECT_HEIGHT);
            case 2:
                return new TriangleShape(new Point(x, y), color, STD_SIZE);
            case 3:
                return new StarShape(new Point(x, y), color, STD_SIZE);
            case 4:
                return new HexagonShape(new Point(x, y), color, STD_SIZE);
            default:
                return new CircleShape(new Point(x, y), color, STD_RADIUS);
        }
    }

    @Override
    public void saveShapes(List<ShapeModel> shapes) {
        // Cette implémentation ne sauvegarde pas les formes
        // car elles sont générées aléatoirement
    }
}