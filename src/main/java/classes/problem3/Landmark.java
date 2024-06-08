package classes.problem3;

import classes.problem1.Point;

/**
 * Represents the wall's control points
 * Plain point with lighting level added
 */
public class Landmark {
    /*
    // TODO: we can create after which mark there should be rest and try to skip these points
     * raz dziennie dookoła płotu powinien przejść strażnik
     * są różnej jasności i zgodnie z ruchem wskazówek zegara wyznaczają trasę strażnika.
     */

    /**
     * position of that stop
     */
    private final Point pos;
    /**
     * brightness level
     */
    private final int brightness;

    @Override
    public String toString() {
        return String.format("(%.0f, %.0f | %d brightness)", pos.getX(), pos.getY(), getBrightness());
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
}
