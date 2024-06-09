package classes.problem3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: better name (maybe Flathead?)

/**
 * Represents character in Flat world
 */
public class Flatguy {
    static final int MAX_ENERGY = 10;
    static final int MIN_ENERGY = 1;
    private final int energy;
    private final int number;
    private int restDaysLeft = 0;

    /**
     * @param number unique number for that character
     * @param energy specifies how much energy this character have
     */
    public Flatguy(int number, int energy) {
        this.energy = energy;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("Flatguy{%d %d energy}", number, energy);
    }

    /**
     * @return returns unique number for that flatguy
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return current energy of character
     */
    public int getEnergy() {
        return energy;
    }


    /**
     * @return returns how many days of rest this flatguy have
     */
    public int getRestDaysLeft() {
        return restDaysLeft;
    }

    /**
     * @param restDaysLeft specifies how many rest days character have left
     */
    public void setRestDaysLeft(int restDaysLeft) {
        this.restDaysLeft = restDaysLeft;
    }

    /**
     * @return if current character is resting
     */
    public boolean isResting() {
        return getRestDaysLeft() != 0;
    }

    /**
     * sends character to rest for a week
     */
    public void sendToRest() {
        this.setRestDaysLeft(WorkSchedule.WEEK);
    }

    /**
     * Decreases number of rest days for that flatguy
     * if rest days is 0 then do nothing
     */
    public void decrementRestDays() {
        if (restDaysLeft > 0) {
            --restDaysLeft;
        }
    }
}
