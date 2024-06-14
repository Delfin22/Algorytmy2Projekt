package classes.problem3;

import classes.problem1.Point;
import classes.problem1.PointsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.SequencedCollection;

public class PointComponent extends JComponent {
    private final int width;
    private final int height;
    private final int padding = 10;
    private List<classes.problem1.Point> points;
    private List<classes.problem1.Point> hull;
    private List<Landmark> hullWithLights;

    public PointComponent(int width, int height) {
        this.width = width;
        this.height = height;

        this.points = new ArrayList<>();
        this.hull = new ArrayList<>();
        this.hullWithLights = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (points != null) {
                    for (classes.problem1.Point point : points) {
                        // Sprawdź, czy kliknięcie jest w pobliżu punktu (w promieniu 5 pikseli)
                        if (e.getPoint().distance(point) < 5) {
                            // Wyświetl współrzędne punktu
                            JOptionPane.showMessageDialog(PointComponent.this,
                                    "(" + point.getX() + ", " + point.getY() + ")", "Współrzędne punktu " + point.getId(), JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width + 2 * padding, height + 2 * padding);
    }

    public void paintComponent(Graphics g) {
        int i = 0;
        if (!points.isEmpty()) {
            System.out.println("LISTA PUNKTOW: " + points);
            for (classes.problem1.Point p : points) {

                p.setId(i);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) p.getX() + padding, (int) p.getY() + padding, 5, 5);

                // g.drawString(Integer.toString(i), (int) p.getX() + padding, (int) p.getY() + 1 + padding);
                i++;
            }
        }
        if (!hull.isEmpty()) {
            System.out.println("LISTA PUNKTOW OBWODOWYCH: " + hull);
            for (i = 1; i < hull.size(); i++) {
                g.setColor(Color.BLUE);
                g.drawLine((int) hull.get(i - 1).getX() + padding,
                        (int) hull.get(i - 1).getY() + padding,
                        (int) hull.get(i).getX() + padding,
                        (int) hull.get(i).getY() + padding);
            }
            g.drawLine((int) hull.getLast().getX() + padding,
                    (int) hull.getLast().getY() + padding,
                    (int) hull.getFirst().getX() + padding,
                    (int) hull.getFirst().getY() + padding);

            if (hullWithLights.size() == hull.size()) {
                g.setColor(Color.DARK_GRAY);
                for (i = 0; i < hull.size(); ++i) {
                    g.drawString(String.format("( %d, %d bright)", i, hullWithLights.get(i).getBrightness()), (int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + 1 + padding);
                }

            }
        }
    }

    public void addPoint(classes.problem1.Point point) {
        points.add(point);
        repaint();
    }

    public void removeLastPoint() {
        if (!points.isEmpty()) {
            points.removeLast();
            hull.clear();
            repaint();
        }
    }

    public void clearPoints() {
        points.clear();
        hull.clear();
        repaint();
    }

    public void setHull() {
        this.hull = PointsTools.findConvexHull(points);
        repaint();
    }

    public List<Landmark> addLights() {
        this.hullWithLights = Problem3Application.addLightsToWall(this.hull);
        return this.hullWithLights;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<classes.problem1.Point> points) {
        this.points = points;
        this.hull = new ArrayList<>();
        repaint();
    }
}

