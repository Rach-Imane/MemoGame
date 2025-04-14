package ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;

public interface LookAndFeelFactory {
    JButton createButton(String text);

    JPanel createPanel();

    JFrame createFrame(String title);

    Color getBackgroundColor();

    Color getForegroundColor();

    Color getAccentColor();
}