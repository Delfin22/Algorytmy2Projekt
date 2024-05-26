package classes.problem3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: better name
public class Flatguy {
    static final int MAX_ENERGY = 10;
    static final int MIN_ENERGY = 1;
    int current;

    private List<Landmark> wall;
    private int energy;
    private int restDaysLeft = 0;
    private ArrayList<Integer> stopPoints;

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

    public void travel() {
        // TODO: it's important to pick correct starting point

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
    }

    public void prepareOptimalPath(List<Landmark> wall) {
        this.wall = wall;

        this.stopPoints = new ArrayList<>();
        Landmark startPoint = Collections.max(wall, (p1, p2) -> Integer.compare(p1.getBrightness(), p2.getBrightness()));
        stopPoints.add(wall.indexOf(startPoint));

        int lastStopBrightness = wall.get(stopPoints.getLast()).getBrightness();
        int nextDarkerIndex = getNextDarkerLandmarkIndex(stopPoints.getLast(), energy, lastStopBrightness);
        while (nextDarkerIndex != -1) {
            stopPoints.add(nextDarkerIndex);

            lastStopBrightness = wall.get(stopPoints.getLast()).getBrightness();
            nextDarkerIndex = getNextDarkerLandmarkIndex(stopPoints.getLast(), energy, lastStopBrightness);
        }


        // TODO: returns optimal starting point and prepares path
        // TODO: find optimal starting point based on energy of worker
    }

    private int getNextDarkerLandmarkIndex(int posIndex, int energy, int lastStopBrightness) {
        int curr = posIndex + 1;
        int darkerThanPrevIndex = -1;
        for (int i = 0; i < energy; ++i) {
            if (curr >= wall.size()) {
                curr = 0;
            } else if (curr == stopPoints.getFirst()) {
                return -1;
            }

            if (wall.get(curr).getBrightness() <= lastStopBrightness) {
                if (darkerThanPrevIndex == -1) {  // first darker one
                    darkerThanPrevIndex = curr;
                } else if (wall.get(darkerThanPrevIndex).getBrightness() < wall.get(curr).getBrightness()) {
                    // else find brightest but darker than last stop
                    darkerThanPrevIndex = curr;
                }
            }


            ++curr;
        }

        // if darkerThanPrevIndex == -1 then flatguy just moves to furthest point away and get rest
        return darkerThanPrevIndex == -1 ? curr : darkerThanPrevIndex;
    }

    public void printPath() {
        for (int i : stopPoints) {
            System.out.printf("%d - %s, \t", i, wall.get(i).toString());
        }
        System.out.println();
    }
}
