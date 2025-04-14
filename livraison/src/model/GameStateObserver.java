package model;

public interface GameStateObserver {
    void onGameStateChanged(GameState gameState);
}