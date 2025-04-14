package view.factory;

import javax.swing.*;
import java.awt.*;

public class MetalUIFactory implements UIFactory {
    private static final Color PRIMARY_COLOR = new Color(0, 120, 215);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private static final Font DEFAULT_FONT = new Font("Dialog", Font.PLAIN, 12);

    @Override
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(DEFAULT_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(DEFAULT_FONT);
        return label;
    }

    @Override
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SECONDARY_COLOR);
        return panel;
    }

    @Override
    public JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    @Override
    public JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(DEFAULT_FONT);
        return textField;
    }

    @Override
    public JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(DEFAULT_FONT);
        return comboBox;
    }

    @Override
    public Color getPrimaryColor() {
        return PRIMARY_COLOR;
    }

    @Override
    public Color getSecondaryColor() {
        return SECONDARY_COLOR;
    }

    @Override
    public Font getDefaultFont() {
        return DEFAULT_FONT;
    }
}