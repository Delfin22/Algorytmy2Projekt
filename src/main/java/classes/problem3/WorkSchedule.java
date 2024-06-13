package classes.problem3;

import classes.problem1.Point;

import java.util.*;

/*
 * PROBLEM:  **********************************************************************************
 *  Ustalić jak najszybciej grafik pracy strażników i jak najmniejszą liczbę odsłuchań melodii dla każdego strażnika
 **********************************************************************************************
 */

/**
 * Represents work schedule for next week
 */
public class WorkSchedule {
    // travel n points, and then check if its brighter than the last one
    //if (wall.get(previous).getBrightness() > wall.get(current).getBrightness()) {
    // zachowuje cala energie
    //} else {
    // traci cala energie i musi odpoczac w tym punkcie aby odzyskac cala energie

    // jak najmniejsza liczba odsluchan -> jak najmniej razy odpoczynek -> jak najmniej przejsc z jasnych do ciemnych

    // znalezc wartosci optymalne z kazdego punktu startowego z kazda wartoscia
    //}


    /*
     * strażnik po minięciu co najwyżej pewnej liczby punktów orientacyjnych musi
     *  zatrzymać się, by dokładniej rozejrzeć się dookoła. Jednak zachowuje całą energię
     *  do pracy tylko wtedy, gdy poprzednim punktem zatrzymania był punkt jaśniejszy
     * od tego, w którym się aktualnie zatrzymuje (nie męczą mu się oczy)
     * W przeciwnym razie całą energię traci i wtedy musi w tym punkcie odpocząć i posłuchać melodii, by energię odzyskać
     */
    // trzeba też wybrać takie punkty zatrzymania się, by liczba odsłuchań melodii przez strażnika była jak najmniejsza

    public static final int REQUIRED_ENERGY = 1;  // by default allow all to work
    public static final int WEEK = 7;
    static final Random rand = new Random();
    final List<Landmark> wall;
    final WorkDay[] schedule;
    Workers workers;

    /**
     * @param residents  inhabitants of flatland
     * @param stopPoints landmarks of build wall that will be guarded
     * @param ndays      number of schedule days
     */
    public WorkSchedule(List<Flatguy> residents, List<Landmark> stopPoints, int ndays, int requiredEnergy) {
        wall = stopPoints;
        workers = new Workers(residents, requiredEnergy);

        schedule = prepareSchedule(ndays);
    }

    public WorkSchedule(List<Flatguy> residents, List<Landmark> stopPoints, int ndays) {
        this(residents, stopPoints, ndays, WorkSchedule.REQUIRED_ENERGY);
    }

    /**
     * @param wall  current wall around string
     * @param guard guard for whom path is gonna be created
     * @return path with least stops for rest
     */
    static public List<StopPoint> prepareOptimalPath(List<Landmark> wall, Flatguy guard) {
        List<StopPoint> path = new ArrayList<>();

        Landmark startPoint = Collections.max(wall, (p1, p2) -> Integer.compare(p1.getBrightness(), p2.getBrightness()));
        path.add(new StopPoint(startPoint, false));

        Landmark next = getNextDarkerLandmark(wall, path, guard.getEnergy());
        while (next != null) {
            boolean restRequired = path.getLast().landmark().getBrightness() < next.getBrightness();
            path.add(new StopPoint(next, restRequired));

            boolean returnToStart = path.getFirst().landmark().equals(path.getLast().landmark());
            if (returnToStart) {
                break;
            }

            next = getNextDarkerLandmark(wall, path, guard.getEnergy());
        }

        return path;
    }

    /**
     * Helper function that finds the brightest landmark but darker than current one in energy distance forward
     *
     * @param wall   current wall around string
     * @param path   current build path
     * @param energy distance in which we are looking for the brightest landmark
     * @return the brightest landmark with brightness level below last one visited landmark
     */
    static private Landmark getNextDarkerLandmark(List<Landmark> wall, List<StopPoint> path, int energy) {
        final Landmark lastStop = path.getLast().landmark();

        Landmark darkest = null;
        Landmark current = null;
        int currIndex = wall.indexOf(path.getLast().landmark());
        for (int i = 0; i < energy; ++i) {
            ++currIndex;

            if (current == path.getFirst().landmark()) {
                return path.getFirst().landmark();  // to be checked by caller
            }

            if (currIndex >= wall.size()) {
                currIndex = 0;
            }

            current = wall.get(currIndex);

            if (current.getBrightness() <= lastStop.getBrightness()) {
                if (darkest == null) {  // first time darker point
                    darkest = current;
                } else if (darkest.getBrightness() <= current.getBrightness()) {  // another darker point
                    // find brightest but darker than last stop
                    darkest = current;
                }
            }
        }

        // if darkerThanPrevIndex == -1 then flatguy just moves to furthest point away and get rest
        return darkest == null ? current : darkest;
    }

    public Workers getWorkers() {
        return workers;
    }

    /**
     * @return returns prepares schedule
     */
    public WorkDay[] getSchedule() {
        return schedule;
    }

    /**
     * prepares schedule for next n days
     * @param ndays for how many days
     * @return returns prepared schedule
     */
    public WorkDay[] prepareSchedule(int ndays) {
        final WorkDay[] schedule = new WorkDay[ndays];
        for (int i = 0; i < ndays; ++i) {
            Flatguy worker = workers.get();
            if (worker == null) {
                schedule[i] = new WorkDay(null, i, null);
                workers.sendCurrToRest();
                continue;
            }

            List<StopPoint> path = prepareOptimalPath(wall, worker);
            schedule[i] = new WorkDay(worker, i, path);
            workers.sendCurrToRest();
        }

        return schedule;
    }
}