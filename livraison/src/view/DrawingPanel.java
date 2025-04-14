package view;

import model.ShapeModel;
import view.factory.LookAndFeelPlugin;
import view.factory.UIFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Panneau de dessin pour afficher les formes
 */
public class DrawingPanel extends JPanel {
    private List<ShapeModel> shapes;
    private Point startPoint;
    private Point endPoint;
    private boolean isDrawing;
    private UIFactory uiFactory;

    public DrawingPanel() {
        this.uiFactory = LookAndFeelPlugin.getCurrentFactory();
        setPreferredSize(new Dimension(800, 600));
        setBackground(uiFactory.getSecondaryColor());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                endPoint = e.getPoint();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                repaint();
            }
        });
    }

    public void setShapes(List<ShapeModel> shapes) {
        this.shapes = shapes;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println(
                "[Panel] paintComponent appelée. Nombre de formes: " + (shapes != null ? shapes.size() : "null")); // DEBUG
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner les formes existantes
        if (shapes != null) {
            for (ShapeModel shape : shapes) {
                g2d.setColor(uiFactory.getPrimaryColor());
                shape.draw(g2d);
            }
        }

        // Dessiner la forme en cours de création
        if (isDrawing && startPoint != null && endPoint != null) {
            g2d.setColor(uiFactory.getPrimaryColor());
            g2d.setStroke(new BasicStroke(2.0f));
            int x = Math.min(startPoint.x, endPoint.x);
            int y = Math.min(startPoint.y, endPoint.y);
            int width = Math.abs(endPoint.x - startPoint.x);
            int height = Math.abs(endPoint.y - startPoint.y);
            g2d.drawRect(x, y, width, height);
        }
    }
}