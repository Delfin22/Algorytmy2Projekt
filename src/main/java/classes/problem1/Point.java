package classes.problem1;

import java.awt.geom.Point2D;

public class Point extends Point2D {
    private int id;
    private double x,y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        setLocation(x,y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point point = (Point) obj;
        return java.lang.Double.compare(point.x, x) == 0 && java.lang.Double.compare(point.y, y) == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
