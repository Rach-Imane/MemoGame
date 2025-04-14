package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant une session de jeu, incluant les scores et les ensembles de
 * formes
 */
public class GameSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Integer> scores;
    private List<ShapeSet> shapeSets;
    private String player1Name;
    private String player2Name;
    private int currentRound;
    private boolean isTwoPlayerMode;
    private int totalRounds;

    public GameSession(String player1Name, String player2Name, boolean isTwoPlayerMode) {
        this.scores = new ArrayList<>();
        this.shapeSets = new ArrayList<>();
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.currentRound = 0;
        this.isTwoPlayerMode = isTwoPlayerMode;
        this.totalRounds = 10; // Par défaut, 10 rounds
    }

    public void addScore(int score) {
        scores.add(score);
        currentRound++;
    }

    public void addShapeSet(ShapeSet shapeSet) {
        shapeSets.add(shapeSet);
    }

    public double getAverageScore() {
        if (scores.isEmpty())
            return 0.0;
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public boolean isGameComplete() {
        return currentRound >= totalRounds;
    }

    public List<Integer> getScores() {
        return new ArrayList<>(scores);
    }

    public List<ShapeSet> getShapeSets() {
        return new ArrayList<>(shapeSets);
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public boolean isTwoPlayerMode() {
        return isTwoPlayerMode;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int rounds) {
        this.totalRounds = rounds;
    }

    public int getPlayer1Score() {
        if (!isTwoPlayerMode)
            return 0;
        int score = 0;
        for (int i = 0; i < scores.size(); i += 2) {
            score += scores.get(i);
        }
        return score;
    }

    public int getPlayer2Score() {
        if (!isTwoPlayerMode)
            return 0;
        int score = 0;
        for (int i = 1; i < scores.size(); i += 2) {
            score += scores.get(i);
        }
        return score;
    }
}