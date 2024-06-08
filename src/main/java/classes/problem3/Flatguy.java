package classes.problem3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: better name (maybe Flathead?)

/**
 * Represents entity in Flat world
 */
public class Flatguy {
    static final int MAX_ENERGY = 10;
    static final int MIN_ENERGY = 1;

    private final int energy;
    private int restDaysLeft = 0;

    /**
     * @param energy specifies how much energy this character have
     */
    public Flatguy(int energy) {
        this.energy = energy;
    }

    /**
     * @return current energy of character
     */
    public int getEnergy() {
        return energy;
    }


    /**
     * @return if current character is resting
     */
    public boolean isResting() {
        return restDaysLeft != 0;
    }

    /**
     * sends character to rest for a week
     */
    public void sendToRest() {
        this.setRestDaysLeft(WorkSchedule.WEEK);
    }

    /**
     * @param restDaysLeft specifies how many rest days character have left
     */
    public void setRestDaysLeft(int restDaysLeft) {
        this.restDaysLeft = restDaysLeft;
    }
}
