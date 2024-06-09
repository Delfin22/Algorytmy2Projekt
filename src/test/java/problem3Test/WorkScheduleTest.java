package problem3Test;

import classes.problem1.Point;
import classes.problem3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class WorkScheduleTest {
    private List<Landmark> getWall() {
        /*
             4 -- 8 (* brightest) -> start point
            /      \
          5          1
            \_    _/
               2
         */
        List<Landmark> wall = new ArrayList<>();
        wall.add(new Landmark(new Point(1, 0), 4));
        wall.add(new Landmark(new Point(3, 0), 8));
        wall.add(new Landmark(new Point(4, 1), 1));
        wall.add(new Landmark(new Point(2, 2), 2));
        wall.add(new Landmark(new Point(0, 1), 5));
        return wall;
    }
    @Test
    void prepareOptimalPathTestForOneEnergy() {
        Flatguy worker1 = new Flatguy(1,1);

        List<Landmark> wall = getWall();
        List<StopPoint> path = WorkSchedule.prepareOptimalPath(wall, worker1);

        List<StopPoint> expPath = Arrays.asList(
                new StopPoint(wall.get(1), false),
                new StopPoint(wall.get(2), false),
                new StopPoint(wall.get(3), true),
                new StopPoint(wall.get(4), true),
                new StopPoint(wall.get(0), false),
                new StopPoint(wall.get(1), true)  // loop
        );

        assertEquals(expPath, path);
    }


    @Test
    void prepareOptimalPathForTwoEnergy() {
        Flatguy worker2 = new Flatguy(0, 2);

        List<Landmark> wall = getWall();
        List<StopPoint> path = WorkSchedule.prepareOptimalPath(wall, worker2);

        List<StopPoint> expPath = Arrays.asList(
                new StopPoint(wall.get(1), false),
                new StopPoint(wall.get(3), false),
                new StopPoint(wall.get(0), true),
                new StopPoint(wall.get(1), true)  // loop
        );

        assertEquals(expPath, path);
    }

    @Test
    void prepareScheduleTest() {
        List<Flatguy> flatguys = new ArrayList<>();
        for (int i = 1; i <= WorkSchedule.WEEK; ++i) {
            flatguys.add(new Flatguy(i, i));
        }

        assertEquals(WorkSchedule.WEEK, flatguys.size());

        WorkSchedule ws = new WorkSchedule(flatguys, getWall(), WorkSchedule.WEEK);
        WorkDay[] schedule = ws.getSchedule();

        assertEquals(WorkSchedule.WEEK, schedule.length);
        for (int i = 0; i < schedule.length; ++i) {
            assertEquals(i, schedule[i].getDay());
            assertNotNull(schedule[i].getGuard());
            assertNotNull(schedule[i].getSchedule());
        }
    }

    @Test
    void prepareScheduleWithInsufficientWorkersTest() {
        List<Flatguy> flatguys = new ArrayList<>();
        for (int i = 0; i < WorkSchedule.WEEK - 4; ++i) {  // minus one that doesn't have energy
            flatguys.add(new Flatguy(i, i));
        }

        assertEquals(3, flatguys.size());

        WorkSchedule ws = new WorkSchedule(flatguys, getWall(), WorkSchedule.WEEK);
        WorkDay[] schedule = ws.getSchedule();

        assertEquals(WorkSchedule.WEEK, schedule.length);
        for (int i = 0; i < schedule.length; ++i) {
            // we only have 2 workers for that week
            if (i < 2) {
                continue;
            }

            assertEquals(i, schedule[i].getDay());
            assertNull(schedule[i].getGuard());
            assertNull(schedule[i].getSchedule());
        }
    }

}