package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Classe gérant l'état du jeu et notifiant les observateurs des changements
 */
public class GameState extends Observable {
    private List<ShapeModel> shapes;
    private boolean isShowingModel;
    private boolean isPlayerTurn;
    private int currentScore;
    private int displayTime;

    public GameState() {
        shapes = new ArrayList<>();
        isShowingModel = false;
        isPlayerTurn = false;
        currentScore = 0;
        displayTime = 10000; // 10 secondes par défaut
    }

    public void addShape(ShapeModel shape) {
        shapes.add(shape);
        notifyObservers();
    }

    public void removeShape(ShapeModel shape) {
        if (shapes.remove(shape)) {
            notifyObservers();
        }
    }

    public List<ShapeModel> getShapes() {
        return new ArrayList<>(shapes);
    }

    public void clearShapes() {
        if (!shapes.isEmpty()) {
            shapes.clear();
            notifyObservers();
        }
    }

    public boolean isShowingModel() {
        return isShowingModel;
    }

    public void setShowingModel(boolean showing) {
        this.isShowingModel = showing;
        notifyObservers();
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.isPlayerTurn = playerTurn;
        notifyObservers();
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int score) {
        this.currentScore = score;
        notifyObservers();
    }

    public int getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(int time) {
        this.displayTime = time;
        notifyObservers();
    }

    public void moveShape(ShapeModel shape, int x, int y) {
        shape.setX(x);
        shape.setY(y);
        notifyObservers();
    }

    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}