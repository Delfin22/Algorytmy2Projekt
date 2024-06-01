package classes.problem3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: better name
public class Flatguy {
    static final int MAX_ENERGY = 10;
    static final int MIN_ENERGY = 1;
    private final int energy;
    private int restDaysLeft = 0;

    /*
     * PROBLEM:  **********************************************************************************
     *  Ustalić jak najszybciej grafik pracy strażników i jak najmniejszą liczbę odsłuchań melodii dla każdego strażnika
     **********************************************************************************************
     */
    public Flatguy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }


    public boolean isResting() {
        return restDaysLeft != 0;
    }

    public void sendToRest() {
        this.setRestDaysLeft(WorkSchedule.WEEK);
    }

    public void setRestDaysLeft(int restDaysLeft) {
        this.restDaysLeft = restDaysLeft;
    }
}
