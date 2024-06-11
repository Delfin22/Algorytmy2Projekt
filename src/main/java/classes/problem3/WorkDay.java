package classes.problem3;

import java.util.List;

/**
 * Represents working day for WorkSchedule
 * Contains information in which day which flatguy will patrol the wall
 *
 * @see WorkSchedule
 */
public class WorkDay {
    /**
     * Patrolling guard in current day
     */
    Flatguy guard;

    /**
     * @return returns index number of that day
     */
    public int getDay() {
        return day;
    }

    /**
     * which day it is
     */
    int day;

    /**
     * @return returns list of stops for current guard
     */
    public List<StopPoint> getSchedule() {
        return stops;
    }

    /**
     * plan of stops for current guard based on his energy
     */
    List<StopPoint> stops;

    /**
     * @return returns guard currently guarding the wall in that Workday
     */
    public Flatguy getGuard() {
        return guard;
    }

    /**
     * Creates work day
     *
     * @param guard guard that will patrol the wall
     * @param day   in which day guard is gonna work
     * @param path  list of stops where guard will stop and look around
     */
    public WorkDay(Flatguy guard, int day, List<StopPoint> path) {
        this.guard = guard;
        this.day = day;
        this.stops = path;
    }

    @Override
    public String toString() {
        String workerInformation = (guard != null) ?
                String.format("Flatguy$%d with %d energy", guard.getNumber(), guard.getEnergy())
                : "No WORKER AVAILABLE";
        return String.format("Day %d: %s\n{%s}", day, workerInformation, stops != null ? stops : "");
    }
}
