package classes.problem3;

import classes.problem1.Point;
import classes.problem1.PointsTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class LandmarkComponent extends JComponent {
    private final int width;
    private final int height;
    private final int padding = 10;
    private List<Landmark> hull;
    private WorkDay workday;


    public LandmarkComponent(int width, int height, List<Landmark> hull) {
        this.width = width;
        this.height = height;
        this.hull = hull;
    }

    public void setHull(List<Landmark> hull) {
        this.hull = hull;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width + 2 * padding, height + 2 * padding);
    }

    public void paintComponent(Graphics g) {
        /*
        int i = 0;
        if (!points.isEmpty()) {
            System.out.println("LISTA PUNKTOW: " + points);
            for (classes.problem1.Point p : points) {

                p.setId(i);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) p.getX() + padding, (int) p.getY() + padding, 5, 5);
                g.drawString(Integer.toString(i), (int) p.getX() + padding, (int) p.getY() + 1 + padding);
                i++;
            }
        }
        */
        if (!hull.isEmpty()) {
            int j = 0;  // stopPoint index
            int i = 0;  // all hull point index
            if (workday.guard == null) {
                // paint only hull
                Landmark p = hull.get(i);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) p.getX() + padding, (int) p.getY() + padding, 5, 5);
                g.drawString(Integer.toString(i), (int) p.getX() + padding, (int) p.getY() + 1 + padding);
                g.drawString(String.format("( %d, %d bright)", i, hull.get(i).getBrightness()), (int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + 1 + padding);

                for (i = 1; i < hull.size(); i++) {
                    g.setColor(Color.darkGray);
                    g.drawLine((int) hull.get(i - 1).getX() + padding,
                            (int) hull.get(i - 1).getY() + padding,
                            (int) hull.get(i).getX() + padding,
                            (int) hull.get(i).getY() + padding);
                    g.fillOval((int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + padding, 5, 5);
                    g.drawString(String.format("( %d, %d bright)", i, hull.get(i).getBrightness()), (int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + 1 + padding);
                }

                g.drawLine((int) hull.getLast().getX() + padding,
                        (int) hull.getLast().getY() + padding,
                        (int) hull.getFirst().getX() + padding,
                        (int) hull.getFirst().getY() + padding);
                return;
            }


            Landmark p = hull.get(i);
            g.setColor(Color.DARK_GRAY);
            g.fillOval((int) p.getX() + padding, (int) p.getY() + padding, 5, 5);
            g.drawString(Integer.toString(i), (int) p.getX() + padding, (int) p.getY() + 1 + padding);
            if (hull.get(i).getPos().equals(workday.stops.get(j))) {
                g.setColor(Color.RED);
                ++j;
            } else {
                g.setColor(Color.darkGray);
            }
            g.drawString(String.format("( %d, %d bright)", i, hull.get(i).getBrightness()), (int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + 1 + padding);

            for (i = 1; i < hull.size(); i++) {
                g.setColor(Color.darkGray);
                g.drawLine((int) hull.get(i - 1).getX() + padding,
                        (int) hull.get(i - 1).getY() + padding,
                        (int) hull.get(i).getX() + padding,
                        (int) hull.get(i).getY() + padding);
                g.setColor(Color.DARK_GRAY);
                g.fillOval((int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + padding, 5, 5);
                g.drawString(String.format("( %d, %d bright)", i, hull.get(i).getBrightness()), (int) hull.get(i).getX() + padding, (int) hull.get(i).getY() + 1 + padding);
            }

            g.setColor(Color.DARK_GRAY);
            g.drawLine((int) hull.getLast().getX() + padding,
                    (int) hull.getLast().getY() + padding,
                    (int) hull.getFirst().getX() + padding,
                    (int) hull.getFirst().getY() + padding);

            // TODO: draw in one loop
            List<StopPoint> stops = workday.getSchedule();
            g.setColor(Color.RED);
            g.drawString(String.format("( %d, %d bright)", 0, stops.get(0).landmark().getBrightness()), (int) stops.get(0).landmark().getX() + padding, (int) stops.get(0).landmark().getY() + 1 + padding);
            for (i = 1; i < stops.size() - 1; ++i) {
                if (stops.get(i).restRequired()) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(Color.MAGENTA);
                }
                g.drawString(String.format("( %d, %d bright)", i, stops.get(i).landmark().getBrightness()), (int) stops.get(i).landmark().getX() + padding, (int) stops.get(i).landmark().getY() + 1 + padding);
            }

            // check if we need rest when returning to beginning
            StopPoint returnToBegin = stops.getLast();
            if (returnToBegin.restRequired()) {
                g.setColor(Color.BLUE);
                g.drawString("________________", (int) stops.get(i).landmark().getX() + padding, (int) stops.get(i).landmark().getY() + 1 + padding);

            }
        }
    }


    public void setWorkDay(WorkDay day) {
        this.workday = day;
        repaint();
    }

    /*
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

    public void addLights() {
        this.hullWithLights = Problem3Application.addLightsToWall(this.hull);
        repaint();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<classes.problem1.Point> points) {
        this.points = points;
        this.hull = new ArrayList<>();
        repaint();
    }
     */

}
