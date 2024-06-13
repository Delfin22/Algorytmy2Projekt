package classes.problem1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.awt.geom.Line2D.ptSegDist;

public class PointComponent extends JComponent {
    private List<Point> points;
    private List<Point> hull;
    public PointComponent() {
        this.points = new ArrayList<>();
        this.hull = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (points != null) {
                    for (Point point : points) {
                        // Sprawdź, czy kliknięcie jest w pobliżu punktu (w promieniu 5 pikseli)
                        if (e.getPoint().distance(point) < 5) {
                            // Wyświetl współrzędne punktu
                            JOptionPane.showMessageDialog(PointComponent.this,
                                    "(" + point.getX() + ", " + point.getY() + ")", "Współrzędne punktu " + point.getId(), JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
                if (hull != null && hull.size() > 1) {
                    for (int i = 0; i < hull.size() - 1; i++) {
                        Point p1 = hull.get(i);
                        Point p2 = hull.get(i + 1);
                        if (isPointNearLine(e.getPoint(), p1, p2)) {
                            double distance = p1.distance(p2);
                            JOptionPane.showMessageDialog(PointComponent.this,
                                    "Odległość między punktami " + p1.getId() +" i "+ p2.getId()+" : " + distance, "Odległość między punktami", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                    // Sprawdź linię między ostatnim a pierwszym punktem
                    Point p1 = hull.getLast();
                    Point p2 = hull.getFirst();
                    if (isPointNearLine(e.getPoint(), p1, p2)) {
                        double distance = p1.distance(p2);
                        JOptionPane.showMessageDialog(PointComponent.this,
                                "Odległość między punktami " + p1.getId() +" i "+ p2.getId()+" : " + distance, "Odległość między punktami", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }
    private boolean isPointNearLine(java.awt.Point mousePoint, Point p1, Point p2) {
        // Sprawdź, czy punkt (kliknięcie myszy) jest blisko linii między p1 a p2
        final double tolerance = 5.0;
        double distance = ptSegDist(p1.getX(), p1.getY(), p2.getX(), p2.getY(), mousePoint.getX(), mousePoint.getY());
        return distance < tolerance;
    }

    public void paintComponent(Graphics g){
        int i = 0;
        if(!points.isEmpty()) {
            System.out.println("LISTA PUNKTOW: " + points);
            for (Point p : points) {
                p.setId(i);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) p.getX(), (int) p.getY(), 5, 5);
                g.drawString(String.format("(%d, %d bright)", i, 0), (int) p.getX(), (int) p.getY() + 1);
                i++;
            }
        }
        if(!hull.isEmpty()){
                System.out.println("LISTA PUNKTOW OBWODOWYCH: " + hull);
                for (i = 1; i < hull.size(); i++) {
                    g.setColor(Color.BLUE);
                    g.drawLine((int) hull.get(i - 1).getX(),
                            (int) hull.get(i - 1).getY(),
                            (int) hull.get(i).getX(),
                            (int) hull.get(i).getY());
                }
                g.drawLine((int) hull.getLast().getX(),
                        (int) hull.getLast().getY(),
                        (int) hull.getFirst().getX(),
                        (int) hull.getFirst().getY());
        }
    }


    public void setPoints(List<Point> points) {
        this.points = points;
        this.hull = new ArrayList<>();
        repaint();
    }
    public void addPoint(Point point){
        points.add(point);
        repaint();
    }
    public void removeLastPoint(){
        if(!points.isEmpty()) {
            points.removeLast();
            hull.clear();
            repaint();
        }
    }

    public void clearPoints(){
        points.clear();
        hull.clear();
        repaint();
    }
    public void setHull(){
        this.hull = PointsTools.findConvexHull(points);
        repaint();
    }

    public List<Point> getPoints() {
        return points;
    }
    public List<Point> getHull(){
        return hull;
    }
}

