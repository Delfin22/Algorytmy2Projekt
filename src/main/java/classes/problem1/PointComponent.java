package classes.problem1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                                    "(" + point.getX() + ", " + point.getY() + ")","Współrzędne punktu " + point.getId(),JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                }
            }
        });
    }


    public void paintComponent(Graphics g){
        int i = 0;
        if(!points.isEmpty()) {
            System.out.println("LISTA PUNKTOW: " + points);
            for (Point p : points) {
                p.setId(i);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) p.getX(), (int) p.getY(), 5, 5);
                g.drawString(Integer.toString(i), (int) p.getX(), (int) p.getY() + 1);
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
}

