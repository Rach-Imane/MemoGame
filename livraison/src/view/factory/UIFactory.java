package view.factory;

import javax.swing.*;
import java.awt.*;

public interface UIFactory {
    JButton createButton(String text);

    JLabel createLabel(String text);

    JPanel createPanel();

    JFrame createFrame(String title);

    JTextField createTextField();

    JComboBox<String> createComboBox(String[] items);

    Color getPrimaryColor();

    Color getSecondaryColor();

    Font getDefaultFont();
}