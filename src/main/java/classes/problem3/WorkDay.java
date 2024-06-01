package classes.problem3;

import java.util.List;

public class WorkDay {
    Flatguy guard;  // currently guarding
    int day;
    List<StopPoint> stops;

    public WorkDay(Flatguy guard, int day, List<StopPoint> path) {
        this.guard = guard;
        this.day = day;
        this.stops = path;
    }


    public void printPath() {
        for (StopPoint stop : stops) {
            System.out.printf("\t%s %s\n", stop.landmark(), stop.restRequired() ? "(Rest required)" : "");
        }
        System.out.println();
    }
}
