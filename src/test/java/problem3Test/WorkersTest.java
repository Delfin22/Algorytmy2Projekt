package problem3Test;

import classes.problem3.Flatguy;
import classes.problem3.WorkSchedule;
import classes.problem3.Workers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkersTest {
    @Test
    void filterWorkersWithSufficientEnergyTest() {
        int requiredEnergy = WorkSchedule.REQUIRED_ENERGY;

        List<Flatguy> flatheads = new ArrayList<>();
        flatheads.add(new Flatguy(0, 0));
        flatheads.add(new Flatguy(1, 2));
        flatheads.add(new Flatguy(2, 5));
        flatheads.add(new Flatguy(3, 1));

        Workers workers = new Workers(flatheads, requiredEnergy);
        assertEquals(3, workers.readyWorkersSize(), "There should be 3 workers at the start");

        assertEquals(2, workers.get().getNumber());
        assertEquals(5, workers.get().getEnergy());
        workers.sendCurrToRest();
        assertEquals(2, workers.readyWorkersSize());
        assertEquals(1, workers.restingWorkersSize());

        assertEquals(1, workers.get().getNumber());
        assertEquals(2, workers.get().getEnergy());
        workers.sendCurrToRest();
        assertEquals(1, workers.readyWorkersSize());
        assertEquals(2, workers.restingWorkersSize());

        assertEquals(3, workers.get().getNumber());
        assertEquals(1, workers.get().getEnergy());
        workers.sendCurrToRest();
        assertEquals(0, workers.readyWorkersSize());
        assertEquals(3, workers.restingWorkersSize());
    }


    @Test
    void InsufficientWorkerTest() {
        int requiredEnergy = WorkSchedule.REQUIRED_ENERGY;

        List<Flatguy> flatheads = new ArrayList<>();
        flatheads.add(new Flatguy(0, 0));
        flatheads.add(new Flatguy(1, 1));

        Workers workers = new Workers(flatheads, requiredEnergy);
        assertEquals(1, workers.readyWorkersSize(), "There should be 1 workers at the start");

        assertEquals(1, workers.get().getNumber());
        assertEquals(1, workers.get().getEnergy());
        workers.sendCurrToRest();
        assertEquals(0, workers.readyWorkersSize());
        assertEquals(1, workers.restingWorkersSize());

        // No workers left
        assertNull(workers.get());
    }


    @Test
    void WorkerComesBackFromRestTest() {
        int requiredEnergy = WorkSchedule.REQUIRED_ENERGY;
        List<Flatguy> flatheads = new ArrayList<>();
        flatheads.add(new Flatguy(1, 1));
        Workers workers = new Workers(flatheads, requiredEnergy);
        assertEquals(1, workers.readyWorkersSize(), "There should be 1 workers at the start");

        assertEquals(1, workers.get().getNumber());
        assertEquals(1, workers.get().getEnergy());
        workers.sendCurrToRest();
        assertEquals(0, workers.readyWorkersSize());
        assertEquals(1, workers.restingWorkersSize());

        // No workers left
        for (int i = 0; i < WorkSchedule.WEEK; ++i) {
            assertNull(workers.get());
            workers.sendCurrToRest();
        }
        // after one week worker should become available
        assertEquals(1, workers.readyWorkersSize());
        assertEquals(1, workers.get().getNumber());
        assertEquals(1, workers.get().getEnergy());

        assertEquals(0, workers.restingWorkersSize());
    }


    @Test
    void RespectEnergyPriorityWhenComingBackTest() {
        int requiredEnergy = WorkSchedule.REQUIRED_ENERGY;
        List<Flatguy> flatheads = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            flatheads.add(new Flatguy(i, i));
        }

        Workers workers = new Workers(flatheads, requiredEnergy);
        assertEquals(10, workers.readyWorkersSize(), "There should be 10 workers at the start");

        assertEquals(10, workers.get().getNumber());
        assertEquals(10, workers.get().getEnergy());
        workers.sendCurrToRest(); // 10 flatguy is resting for week

        for (int i = 0; i < WorkSchedule.WEEK; ++i) {
            // skip through other workers
            assertEquals(9 - i, workers.get().getNumber());
            assertEquals(9 - i, workers.get().getEnergy());
            workers.sendCurrToRest();
        }

        // after one week best worker should become available and on top
        assertEquals(10, workers.get().getNumber());
        assertEquals(10, workers.get().getEnergy());
    }
}