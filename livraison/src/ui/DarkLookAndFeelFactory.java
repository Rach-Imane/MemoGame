package ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;

public class DarkLookAndFeelFactory implements LookAndFeelFactory {
    private static final Color BACKGROUND = new Color(30, 30, 30);
    private static final Color FOREGROUND = new Color(200, 200, 200);
    private static final Color ACCENT = new Color(0, 150, 255);

    @Override
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ACCENT);
        button.setForeground(FOREGROUND);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND);
        panel.setForeground(FOREGROUND);
        return panel;
    }

    @Override
    public JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().setBackground(BACKGROUND);
        frame.getContentPane().setForeground(FOREGROUND);
        return frame;
    }

    @Override
    public Color getBackgroundColor() {
        return BACKGROUND;
    }

    @Override
    public Color getForegroundColor() {
        return FOREGROUND;
    }

    @Override
    public Color getAccentColor() {
        return ACCENT;
    }
}