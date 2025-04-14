package view;

import model.GameState;
import model.ShapeModel;
import controller.GameController;
import view.factory.LookAndFeelPlugin;
import view.factory.UIFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.swing.border.*;
import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Vue principale du jeu
 */
public class GameView extends JFrame implements Observer {
    private GameState gameState;
    private GameController gameController;
    private DrawingPanel drawingPanel;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton undoButton;
    private JButton redoButton;
    private JButton addCircleButton;
    private JButton addRectangleButton;
    private JButton validateButton;
    private UIFactory uiFactory;
    private Timer countdownTimer;
    private int remainingTime;
    private JComboBox<String> scoreStrategyCombo;
    private JButton addTriangleButton;
    private JButton addStarButton;
    private JButton addHexagonButton;
    private JComboBox<Color> circleColorCombo;
    private JComboBox<Color> rectangleColorCombo;
    private JComboBox<Color> triangleColorCombo;
    private JComboBox<Color> starColorCombo;
    private JComboBox<Color> hexagonColorCombo;
    private JButton newSessionButton;
    private JLabel currentPlayerLabel;
    private static final Color[] AVAILABLE_COLORS = {
            new Color(255, 0, 0), // Rouge
            new Color(0, 255, 0), // Vert
            new Color(0, 0, 255) // Bleu
    };

    public GameView(GameState gameState, GameController controller) {
        this.gameState = gameState;
        this.gameController = controller;
        this.uiFactory = LookAndFeelPlugin.getCurrentFactory();
        this.remainingTime = 0;
        gameState.addObserver(this);

        // Initialiser le timer de compte à rebours
        countdownTimer = new Timer(1000, e -> {
            if (remainingTime > 0) {
                remainingTime--;
                timerLabel.setText("Temps restant : " + remainingTime + "s");
            } else {
                countdownTimer.stop();
                if (gameState.isShowingModel()) {
                    gameState.setShowingModel(false);
                    gameState.setPlayerTurn(true);
                    gameState.clearShapes();
                }
            }
        });
        countdownTimer.setRepeats(true);
        countdownTimer.setCoalesce(true);

        setTitle("MemoGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Création du menu
        JMenuBar menuBar = new JMenuBar();
        JMenu lookAndFeelMenu = new JMenu("Look & Feel");
        for (String laf : LookAndFeelPlugin.getAvailableLookAndFeels()) {
            JMenuItem item = new JMenuItem(laf);
            item.addActionListener(e -> {
                LookAndFeelPlugin.setCurrentFactory(laf);
                this.uiFactory = LookAndFeelPlugin.getCurrentFactory();
                updateUI();
            });
            lookAndFeelMenu.add(item);
        }
        menuBar.add(lookAndFeelMenu);
        setJMenuBar(menuBar);

        // Création des composants avec la factory
        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(800, 600));

        // Panel pour les contrôles principaux
        JPanel mainControlPanel = new JPanel(new BorderLayout(10, 10));
        mainControlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel pour les boutons de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startButton = uiFactory.createButton("Démarrer");
        startButton.addActionListener(e -> gameController.startGame());
        controlPanel.add(startButton);

        undoButton = uiFactory.createButton("Annuler");
        undoButton.addActionListener(e -> gameController.undo());
        controlPanel.add(undoButton);

        redoButton = uiFactory.createButton("Rétablir");
        redoButton.addActionListener(e -> gameController.redo());
        controlPanel.add(redoButton);

        validateButton = uiFactory.createButton("Valider");
        validateButton.addActionListener(e -> gameController.validateAttempt());
        controlPanel.add(validateButton);

        newSessionButton = uiFactory.createButton("Nouvelle Session");
        newSessionButton.addActionListener(e -> showSessionDialog());
        controlPanel.add(newSessionButton);

        mainControlPanel.add(controlPanel, BorderLayout.NORTH);

        // Panel pour les informations du jeu
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scoreLabel = new JLabel("Score: 0");
        timerLabel = new JLabel("Temps restant : 0s");
        currentPlayerLabel = new JLabel("Tour de : -");
        infoPanel.add(scoreLabel);
        infoPanel.add(timerLabel);
        infoPanel.add(currentPlayerLabel);
        mainControlPanel.add(infoPanel, BorderLayout.CENTER);

        // Panel pour les outils de dessin
        JPanel toolsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        toolsPanel.setBorder(BorderFactory.createTitledBorder("Outils de dessin"));

        // Panel pour les boutons de forme
        JPanel shapeButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addCircleButton = uiFactory.createButton("Cercle");
        addRectangleButton = uiFactory.createButton("Rectangle");
        addTriangleButton = uiFactory.createButton("Triangle");
        addStarButton = uiFactory.createButton("Étoile");
        addHexagonButton = uiFactory.createButton("Hexagone");

        shapeButtonsPanel.add(addCircleButton);
        shapeButtonsPanel.add(addRectangleButton);
        shapeButtonsPanel.add(addTriangleButton);
        shapeButtonsPanel.add(addStarButton);
        shapeButtonsPanel.add(addHexagonButton);

        toolsPanel.add(shapeButtonsPanel);

        // Panel pour les sélecteurs de couleur
        JPanel colorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        circleColorCombo = new JComboBox<>(AVAILABLE_COLORS);
        rectangleColorCombo = new JComboBox<>(AVAILABLE_COLORS);
        triangleColorCombo = new JComboBox<>(AVAILABLE_COLORS);
        starColorCombo = new JComboBox<>(AVAILABLE_COLORS);
        hexagonColorCombo = new JComboBox<>(AVAILABLE_COLORS);

        // Configurer le rendu des couleurs pour chaque combobox
        styleColorComboBox(circleColorCombo);
        styleColorComboBox(rectangleColorCombo);
        styleColorComboBox(triangleColorCombo);
        styleColorComboBox(starColorCombo);
        styleColorComboBox(hexagonColorCombo);

        colorPanel.add(new JLabel("Couleur Cercle:"));
        colorPanel.add(circleColorCombo);
        colorPanel.add(new JLabel("Couleur Rectangle:"));
        colorPanel.add(rectangleColorCombo);
        colorPanel.add(new JLabel("Couleur Triangle:"));
        colorPanel.add(triangleColorCombo);
        colorPanel.add(new JLabel("Couleur Étoile:"));
        colorPanel.add(starColorCombo);
        colorPanel.add(new JLabel("Couleur Hexagone:"));
        colorPanel.add(hexagonColorCombo);

        toolsPanel.add(colorPanel);
        mainControlPanel.add(toolsPanel, BorderLayout.SOUTH);

        // Ajout des composants à la fenêtre
        add(mainControlPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        // Configuration des événements de souris
        setupEventHandlers();

        pack();
        setLocationRelativeTo(null);
    }

    private void showSessionDialog() {
        GameSessionView sessionDialog = new GameSessionView(this, gameController);
        sessionDialog.setVisible(true);
    }

    private void setupEventHandlers() {
        startButton.addActionListener(e -> gameController.startGame());
        undoButton.addActionListener(e -> gameController.undo());
        redoButton.addActionListener(e -> gameController.redo());
        validateButton.addActionListener(e -> gameController.validateAttempt());

        // Gestionnaires pour les boutons de formes
        addCircleButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            Color selectedColor = (Color) circleColorCombo.getSelectedItem();
            gameController.createCircle(center, selectedColor);
        });

        addRectangleButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            Color selectedColor = (Color) rectangleColorCombo.getSelectedItem();
            gameController.createRectangle(center, selectedColor);
        });

        addTriangleButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            Color selectedColor = (Color) triangleColorCombo.getSelectedItem();
            gameController.createTriangle(center, selectedColor);
        });

        addStarButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            Color selectedColor = (Color) starColorCombo.getSelectedItem();
            gameController.createStar(center, selectedColor);
        });

        addHexagonButton.addActionListener(e -> {
            Point center = new Point(drawingPanel.getWidth() / 2, drawingPanel.getHeight() / 2);
            Color selectedColor = (Color) hexagonColorCombo.getSelectedItem();
            gameController.createHexagon(center, selectedColor);
        });
    }

    public void setController(GameController controller) {
        this.gameController = controller;
        setupEventHandlers();
    }

    private void updateUI() {
        // Mettre à jour les couleurs et styles des composants
        drawingPanel.setBackground(uiFactory.getSecondaryColor());
        scoreLabel.setFont(uiFactory.getDefaultFont());
        timerLabel.setFont(uiFactory.getDefaultFont());
        startButton.setBackground(uiFactory.getPrimaryColor());
        undoButton.setBackground(uiFactory.getPrimaryColor());
        redoButton.setBackground(uiFactory.getPrimaryColor());
        addCircleButton.setBackground(uiFactory.getPrimaryColor());
        addRectangleButton.setBackground(uiFactory.getPrimaryColor());
        validateButton.setBackground(uiFactory.getPrimaryColor());
        repaint();
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(() -> {
            drawingPanel.setShapes(gameState.getShapes());
            scoreLabel.setText("Score: " + gameState.getCurrentScore());

            if (gameController.isTwoPlayerMode()) {
                String currentPlayer = gameController.getCurrentPlayer() == 1
                        ? gameController.getCurrentSession().getPlayer1Name()
                        : gameController.getCurrentSession().getPlayer2Name();
                String action = gameState.isShowingModel() ? "dessine" : "reproduit";
                currentPlayerLabel.setText("Tour de : " + currentPlayer + " (" + action + ")");
            } else {
                currentPlayerLabel.setText("Tour de : " + gameController.getCurrentSession().getPlayer1Name());
            }

            // Mettre à jour l'état des boutons
            startButton.setEnabled(!gameState.isShowingModel() && !gameState.isPlayerTurn());
            validateButton.setEnabled(gameState.isPlayerTurn());
            undoButton.setEnabled(gameController.canUndo());
            redoButton.setEnabled(gameController.canRedo());

            // Mise à jour du timer
            if (gameState.isShowingModel()) {
                remainingTime = gameState.getDisplayTime() / 1000;
                timerLabel.setText("Temps restant : " + remainingTime + "s");
                countdownTimer.start();
            } else {
                countdownTimer.stop();
                timerLabel.setText("Temps restant : 0s");
            }
        });
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public JComboBox<Color> getCircleColorCombo() {
        return circleColorCombo;
    }

    // Classe interne pour le rendu des couleurs dans les JComboBox
    private class ColorComboBoxRenderer extends DefaultListCellRenderer {
        private static final int ICON_SIZE = 20;
        private static final int PADDING = 5;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (value instanceof Color) {
                Color color = (Color) value;

                // Créer une icône colorée
                BufferedImage image = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = image.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillOval(0, 0, ICON_SIZE, ICON_SIZE);
                g2d.setColor(Color.GRAY);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawOval(0, 0, ICON_SIZE - 1, ICON_SIZE - 1);
                g2d.dispose();

                label.setIcon(new ImageIcon(image));
                label.setIconTextGap(PADDING);
                label.setFont(new Font("Arial", Font.BOLD, 12));
                label.setText(getColorName(color));

                if (isSelected) {
                    label.setBackground(new Color(230, 230, 230));
                } else {
                    label.setBackground(Color.WHITE);
                }
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
            }
            return label;
        }

        private String getColorName(Color color) {
            if (color.equals(new Color(255, 0, 0)))
                return "Rouge";
            if (color.equals(new Color(0, 255, 0)))
                return "Vert";
            if (color.equals(new Color(0, 0, 255)))
                return "Bleu";
            if (color.equals(new Color(255, 255, 0)))
                return "Jaune";
            if (color.equals(new Color(255, 0, 255)))
                return "Magenta";
            if (color.equals(new Color(0, 255, 255)))
                return "Cyan";
            if (color.equals(new Color(255, 165, 0)))
                return "Orange";
            if (color.equals(new Color(128, 0, 128)))
                return "Violet";
            if (color.equals(new Color(0, 128, 0)))
                return "Vert foncé";
            if (color.equals(new Color(128, 128, 128)))
                return "Gris";
            if (color.equals(new Color(255, 192, 203)))
                return "Rose";
            if (color.equals(new Color(139, 69, 19)))
                return "Marron";
            if (color.equals(new Color(0, 0, 0)))
                return "Noir";
            if (color.equals(new Color(255, 255, 255)))
                return "Blanc";
            return "Couleur inconnue";
        }
    }

    private void styleColorComboBox(JComboBox<Color> comboBox) {
        comboBox.setPreferredSize(new Dimension(120, 30));
        comboBox.setBackground(Color.WHITE);
        comboBox.setRenderer(new ColorComboBoxRenderer());
        comboBox.setMaximumRowCount(14); // Nombre de couleurs disponibles
    }
}