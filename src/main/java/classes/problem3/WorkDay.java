package classes.problem3;

import java.util.List;

public class WorkDay {
    Flatguy guard;  // currently guarding
    int daynumber;

    List<StopPoint> indexesOfStops;

    public WorkDay(Flatguy guard, int daynumber, List<StopPoint> path) {
        this.guard = guard;
        this.daynumber = daynumber;
        this.indexesOfStops = path;
    }

    public void printPath() {
        for (StopPoint stop : indexesOfStops) {
            System.out.printf("%s %s\n",
                    stop.landmark(),
                    stop.restRequired() ? "(Rest required)" : "");
        }
        System.out.println();
    }
}
