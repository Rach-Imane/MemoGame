package view;

import model.GameSession;
import controller.GameController;
import view.factory.LookAndFeelPlugin;
import view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Vue pour gérer les sessions de jeu
 */
public class GameSessionView extends JDialog {
    private GameController gameController;
    private UIFactory uiFactory;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JCheckBox twoPlayerModeCheck;
    private JSpinner roundsSpinner;
    private JButton startButton;
    private JButton cancelButton;

    public GameSessionView(JFrame parent, GameController gameController) {
        super(parent, "Configuration de la partie", true);
        this.gameController = gameController;
        this.uiFactory = LookAndFeelPlugin.getCurrentFactory();

        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel pour les paramètres du joueur
        JPanel playerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        JLabel titleLabel = new JLabel("Bienvenue dans MemoGame !");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        playerPanel.add(titleLabel, gbc);

        // Joueur 1
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        playerPanel.add(new JLabel("Votre nom :"), gbc);

        gbc.gridx = 1;
        player1NameField = new JTextField(20);
        playerPanel.add(player1NameField, gbc);

        // Mode deux joueurs
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        twoPlayerModeCheck = new JCheckBox("Jouer à deux");
        twoPlayerModeCheck.addActionListener(e -> updatePlayer2Field());
        playerPanel.add(twoPlayerModeCheck, gbc);

        // Joueur 2
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        playerPanel.add(new JLabel("Nom du deuxième joueur :"), gbc);

        gbc.gridx = 1;
        player2NameField = new JTextField(20);
        player2NameField.setEnabled(false);
        playerPanel.add(player2NameField, gbc);

        // Nombre de rounds
        gbc.gridx = 0;
        gbc.gridy = 4;
        playerPanel.add(new JLabel("Nombre de manches :"), gbc);

        gbc.gridx = 1;
        roundsSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 20, 1));
        playerPanel.add(roundsSpinner, gbc);

        mainPanel.add(playerPanel, BorderLayout.CENTER);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButton = new JButton("Commencer la partie");
        startButton.addActionListener(e -> startSession());
        cancelButton = new JButton("Quitter");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updatePlayer2Field() {
        player2NameField.setEnabled(twoPlayerModeCheck.isSelected());
    }

    private void startSession() {
        String player1Name = player1NameField.getText().trim();
        String player2Name = player2NameField.getText().trim();
        boolean isTwoPlayerMode = twoPlayerModeCheck.isSelected();

        if (player1Name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer votre nom",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isTwoPlayerMode && player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer le nom du deuxième joueur",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Démarrer la session
        gameController.startNewSession(player1Name, player2Name, isTwoPlayerMode);

        // Afficher la fenêtre principale du jeu
        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent != null) {
            parent.dispose();
        }
        dispose();
    }
}