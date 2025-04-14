package view.factory;

import javax.swing.*;
import java.awt.*;

public class DarkUIFactory implements UIFactory {
    private static final Color PRIMARY_COLOR = new Color(70, 70, 70);
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30);
    private static final Color TEXT_COLOR = new Color(200, 200, 200);
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    @Override
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(DEFAULT_FONT);
        return button;
    }

    @Override
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
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
        frame.getContentPane().setBackground(SECONDARY_COLOR);
        return frame;
    }

    @Override
    public JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(PRIMARY_COLOR);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setFont(DEFAULT_FONT);
        return textField;
    }

    @Override
    public JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(PRIMARY_COLOR);
        comboBox.setForeground(TEXT_COLOR);
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