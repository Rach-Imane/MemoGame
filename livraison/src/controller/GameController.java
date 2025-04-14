package controller;

import model.GameState;
import model.ShapeModel;
import model.RectangleShape;
import model.CircleShape;
import model.TriangleShape;
import model.StarShape;
import model.HexagonShape;
import model.GameSession;
import model.GameSessionManager;
import model.ShapeSet;
import strategy.ShapeGenerationStrategy;
import evaluation.ScoreStrategy;
import command.CommandManager;
import command.AddShapeCommand;
import command.MoveShapeCommand;
import view.GameView;
import view.DrawingPanel;
import controller.state.InteractionState;
import controller.state.IdleState;

import java.awt.Point;
import java.awt.Color;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 * Contrôleur principal du jeu
 */
public class GameController {
    private GameState gameState;
    private GameView gameView;
    private InteractionState currentState; // L'état actuel
    private ShapeGenerationStrategy shapeStrategy;
    private ScoreStrategy scoreStrategy;
    private CommandManager commandManager;
    private Timer currentTimer;
    private ShapeModel selectedShape;
    private Point dragStart;
    private List<ShapeModel> modelShapes; // Pour mémoriser les formes initiales
    private GameSession currentSession;
    private GameSessionManager sessionManager;
    private boolean isTwoPlayerMode;
    private int currentPlayer;
    private boolean timerStarted = false;
    private long timerStartTime;
    private boolean isNewPhase = true;
    private long phaseStartTime;
    private boolean isPhaseActive = false;
    private boolean isDrawingPhase = false;

    public GameController(GameState gameState, GameView gameView) {
        this.gameState = gameState;
        this.gameView = gameView;
        this.commandManager = new CommandManager();
        this.currentState = new controller.state.IdleState(); // État initial
        this.sessionManager = new GameSessionManager();
        this.isTwoPlayerMode = false;
        this.currentPlayer = 1;

        // Configuration des stratégies
        this.shapeStrategy = new strategy.RandomShapeStrategy();
        this.scoreStrategy = evaluation.ScorePlugin.getCurrentStrategy();

        // Configuration du timer
        this.currentTimer = null;

        // Configuration des événements de souris
        if (this.gameView != null) {
            setupMouseListeners();
        }
    }

    // Ajouter une méthode pour définir la vue après la création
    public void setView(GameView view) {
        this.gameView = view;
        if (this.gameView != null) {
            setupMouseListeners(); // Configurer les listeners une fois la vue disponible
        }
    }

    private void setupMouseListeners() {
        DrawingPanel drawingPanel = gameView.getDrawingPanel();

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentState != null) {
                    currentState.handleMousePressed(e, GameController.this);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentState != null) {
                    currentState.handleMouseReleased(e, GameController.this);
                }
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentState != null) {
                    currentState.handleMouseDragged(e, GameController.this);
                }
            }
        });
    }

    public ShapeModel findShapeAt(Point p) {
        // Parcourir les formes dans l'ordre inverse pour sélectionner celle du dessus
        List<ShapeModel> shapes = gameState.getShapes();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            ShapeModel shape = shapes.get(i);
            if (shape.contains(p)) {
                return shape;
            }
        }
        return null;
    }

    public void startNewSession(String player1Name, String player2Name, boolean isTwoPlayerMode) {
        this.isTwoPlayerMode = isTwoPlayerMode;
        this.currentSession = new GameSession(player1Name, player2Name, isTwoPlayerMode);
        this.currentPlayer = 1;
        startGame();
    }

    public void startGame() {
        if (currentTimer != null) {
            currentTimer.cancel();
            currentTimer = null;
        }

        gameState.clearShapes();
        this.modelShapes = null;
        this.isPhaseActive = true;
        this.phaseStartTime = System.currentTimeMillis();

        if (isTwoPlayerMode) {
            gameState.setShowingModel(true);
            gameState.setPlayerTurn(true);
            gameState.setDisplayTime(10000);
        } else {
            gameState.setShowingModel(true);
            gameState.setPlayerTurn(false);

            List<ShapeModel> generatedShapes;
            if (currentSession != null && !currentSession.getShapeSets().isEmpty()) {
                ShapeSet currentSet = currentSession.getShapeSets().get(currentSession.getCurrentRound());
                generatedShapes = new ArrayList<>(currentSet.getShapes());
            } else {
                generatedShapes = shapeStrategy.generateShapes();
            }

            this.modelShapes = new ArrayList<>(generatedShapes);

            for (ShapeModel shape : generatedShapes) {
                commandManager.executeCommand(new AddShapeCommand(gameState, shape));
            }

            gameState.setDisplayTime(10000);
        }

        startTimer();
    }

    private void startTimer() {
        // Créer et démarrer un nouveau timer
        currentTimer = new Timer();
        currentTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (isTwoPlayerMode) {
                        // Fin du tour du joueur actuel
                        // Stocker les formes dessinées comme modèle
                        modelShapes = new ArrayList<>(gameState.getShapes());

                        // Changer de joueur
                        currentPlayer = (currentPlayer == 1) ? 2 : 1;

                        // Démarrer le tour du joueur suivant
                        startGame();
                    } else {
                        // Mode solo
                        gameState.setShowingModel(false);
                        gameState.setPlayerTurn(true);
                        gameState.clearShapes();
                    }
                });
            }
        }, gameState.getDisplayTime());
    }

    // Méthode pour vérifier si le timer est toujours actif
    public boolean isTimerActive() {
        if (!isPhaseActive || currentTimer == null) {
            return false;
        }
        long elapsedTime = System.currentTimeMillis() - phaseStartTime;
        return elapsedTime < gameState.getDisplayTime();
    }

    // Méthode pour obtenir le temps restant
    public long getRemainingTime() {
        if (!isPhaseActive || currentTimer == null) {
            return 0;
        }
        long elapsedTime = System.currentTimeMillis() - phaseStartTime;
        return Math.max(0, gameState.getDisplayTime() - elapsedTime);
    }

    public void evaluateScore() {
        int score = scoreStrategy.calculateScore(
                modelShapes,
                gameState.getShapes());
        gameState.setCurrentScore(score);
    }

    public GameState getGameState() {
        return gameState;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCurrentState(InteractionState newState) {
        this.currentState = newState;
    }

    // Pour MovingShapeState
    public void setSelectedShape(ShapeModel shape) {
        this.selectedShape = shape;
    }

    public ShapeModel getSelectedShape() {
        return selectedShape;
    }

    public void setDragStart(Point point) {
        this.dragStart = point;
    }

    public Point getDragStart() {
        return dragStart;
    }

    // bouton "Valider"
    public void validateAttempt() {
        if (!gameState.isPlayerTurn() || modelShapes == null) {
            return;
        }

        evaluateScore();

        if (currentSession != null) {
            currentSession.addScore(gameState.getCurrentScore());

            if (isTwoPlayerMode) {
                // Fin du tour du joueur actuel
                // Stocker les formes dessinées comme modèle
                modelShapes = new ArrayList<>(gameState.getShapes());

                // Changer de joueur
                currentPlayer = (currentPlayer == 1) ? 2 : 1;

                // Démarrer le tour du joueur suivant
                startGame();
            } else {
                // Mode solo
                if (currentSession.isGameComplete()) {
                    // Fin de la session
                    sessionManager.saveSession(currentSession);
                    gameState.setPlayerTurn(false);
                    setCurrentState(new controller.state.IdleState());
                } else {
                    // Passer au round suivant
                    startGame();
                }
            }
        } else {
            gameState.setPlayerTurn(false);
            setCurrentState(new controller.state.IdleState());
        }
    }

    public void executeCommand(AddShapeCommand command) {
        commandManager.executeCommand(command);
    }

    // Déléguer à CommandManager
    public boolean canUndo() {
        return commandManager.canUndo();
    }

    // Déléguer à CommandManager
    public boolean canRedo() {
        return commandManager.canRedo();
    }

    public void undo() {
        if (commandManager.canUndo()) {
            commandManager.undo();
        }
    }

    public void redo() {
        if (commandManager.canRedo()) {
            commandManager.redo();
        }
    }

    public void setScoreStrategy(String strategyName) {
        evaluation.ScorePlugin.setCurrentStrategy(strategyName);
        this.scoreStrategy = evaluation.ScorePlugin.getCurrentStrategy();
    }

    public String[] getAvailableScoreStrategies() {
        return evaluation.ScorePlugin.getAvailableStrategies();
    }

    public void createCircle(Point position, Color color) {
        if (!isPhaseActive) {
            return;
        }
        CircleShape circle = new CircleShape(position, color, 20);
        commandManager.executeCommand(new AddShapeCommand(gameState, circle));
    }

    public void createRectangle(Point position, Color color) {
        if (!isPhaseActive) {
            return;
        }
        RectangleShape rectangle = new RectangleShape(position, color, 40, 40);
        commandManager.executeCommand(new AddShapeCommand(gameState, rectangle));
    }

    public void createTriangle(Point position, Color color) {
        if (!isPhaseActive) {
            return;
        }
        TriangleShape triangle = new TriangleShape(position, color);
        commandManager.executeCommand(new AddShapeCommand(gameState, triangle));
    }

    public void createStar(Point position, Color color) {
        if (!isPhaseActive) {
            return;
        }
        StarShape star = new StarShape(position, color);
        commandManager.executeCommand(new AddShapeCommand(gameState, star));
    }

    public void createHexagon(Point position, Color color) {
        if (!isPhaseActive) {
            return;
        }
        HexagonShape hexagon = new HexagonShape(position, color);
        commandManager.executeCommand(new AddShapeCommand(gameState, hexagon));
    }

    public void saveCurrentShapeSet(String name) {
        if (modelShapes != null) {
            // Calculer la difficulté en fonction du nombre de formes et de leur complexité
            int difficulty = Math.min(5, Math.max(1, modelShapes.size()));
            ShapeSet shapeSet = new ShapeSet(name, modelShapes, difficulty);
            sessionManager.saveShapeSet(shapeSet);
        }
    }

    public List<ShapeSet> loadAllShapeSets() {
        return sessionManager.loadAllShapeSets();
    }

    public void loadShapeSet(String name) {
        ShapeSet shapeSet = sessionManager.loadShapeSet(name);
        if (shapeSet != null && currentSession != null) {
            currentSession.addShapeSet(shapeSet);
        }
    }

    public GameSession getCurrentSession() {
        return currentSession;
    }

    public boolean isTwoPlayerMode() {
        return isTwoPlayerMode;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public GameView getGameView() {
        return gameView;
    }

    public Color getSelectedColor() {
        return (Color) gameView.getCircleColorCombo().getSelectedItem();
    }
}