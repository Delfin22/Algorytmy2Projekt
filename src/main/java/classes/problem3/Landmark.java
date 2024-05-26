package classes.problem3;

import classes.problem1.Point;

public class Landmark {
    /*
    // TODO: we can create after which mark there should be rest and try to skip these points
     * raz dziennie dookoła płotu powinien przejść strażnik
     * są różnej jasności i zgodnie z ruchem wskazówek zegara wyznaczają trasę strażnika.
     */
    private final Point pos;

    @Override
    public String toString() {
        return String.format("(%.0f, %.0f | %d)", pos.getX(), pos.getY(), getBrightness());
    }

    public int getBrightness() {
        return brightness;
    }

    private final int brightness;

    public Landmark(Point pos, int brightness) {
        this.pos = pos;
        this.brightness = brightness;
    }


}
