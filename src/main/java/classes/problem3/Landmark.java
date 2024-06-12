package classes.problem3;

import classes.problem1.Point;

/**
 * Represents the wall's control points
 * Plain point with lighting level added
 */
public class Landmark {
    /*
     * raz dziennie dookoła płotu powinien przejść strażnik
     * są różnej jasności i zgodnie z ruchem wskazówek zegara wyznaczają trasę strażnika.
     */

    public static int MIN_BRIGHTNESS = 1;
    public static int MAX_BRIGHTNESS = 10;

    /**
     * position of that stop
     */
    private final Point pos;
    /**
     * brightness level
     */
    private final int brightness;

    /**
     * @return String representation of Landmark
     */
    @Override
    public String toString() {
        return String.format("((%.2f, %.2f) | %d brightness)", pos.getX(), pos.getY(), getBrightness());
    }

    /**
     * @return brightness level of that point
     */
    public int getBrightness() {
        return brightness;
    }


    /**
     * @param pos position of stop
     * @param brightness brightness level of that stop
     */
    public Landmark(Point pos, int brightness) {
        this.pos = pos;
        this.brightness = brightness;
    }

    double getX() {
        return pos.getX();
    }

    public Point getPos() {
        return pos;
    }

    double getY() {
        return pos.getY();
    }
}
