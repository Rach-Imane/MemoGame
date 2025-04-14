package main;

import model.GameState;
import controller.GameController;
import view.GameView;
import view.GameSessionView;
import view.factory.LookAndFeelPlugin;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principale qui lance l'application
 */
public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Appliquer le Look & Feel Nimbus
                LookAndFeelPlugin.setCurrentFactory("Nimbus");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Créer les composants principaux
            GameState gameState = new GameState();
            GameView gameView = new GameView(gameState, null);
            GameController gameController = new GameController(gameState, gameView);
            gameView.setController(gameController);

            // Configurer la fenêtre principale
            gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameView.setSize(1024, 768);
            gameView.setLocationRelativeTo(null);

            // Créer et afficher la fenêtre de configuration de session
            GameSessionView sessionView = new GameSessionView(gameView, gameController);
            sessionView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    gameView.setVisible(true);
                }
            });
            sessionView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            sessionView.setLocationRelativeTo(null);
            sessionView.setVisible(true);
        });
    }
}