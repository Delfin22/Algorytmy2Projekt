package classes.problem3;

import java.util.List;

/**
 * Represents working day for WorkSchedule
 * Contains information in which day which flatguy will patrol the wall
 * @see WorkSchedule
 */
public class WorkDay {
    /**
     * Patrolling guard in current day
     */
    Flatguy guard;
    /**
     * which day it is
     */
    int day;
    /**
     * plan of stops for current guard based on his energy
     */
    List<StopPoint> stops;

    /**
     * Creates work day
     * @param guard guard that will patrol the wall
     * @param day in which day guard is gonna work
     * @param path list of stops where guard will stop and look around
     */
    public WorkDay(Flatguy guard, int day, List<StopPoint> path) {
        this.guard = guard;
        this.day = day;
        this.stops = path;
    }


    /**
     * Help function that
     */
    public void printPath() {
        for (StopPoint stop : stops) {
            System.out.printf("\t%s %s\n", stop.landmark(), stop.restRequired() ? "(Rest required)" : "");
        }
        System.out.println();
    }
}
