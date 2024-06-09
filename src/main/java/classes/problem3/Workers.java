package classes.problem3;

import java.util.*;

/**
 * This class takes all living flatheads in land,
 * filters those that are able to work and then manages them
 */
public class Workers {
    Queue<Flatguy> workers;
    List<Flatguy> restingWorkers;

    /**
     * Constructor that takes all flatguys and filters only those that are suitable for work
     *
     * @param flatheads      all living things in flatland
     * @param requiredEnergy get only those above requiredEnergy
     */
    public Workers(List<Flatguy> flatheads, int requiredEnergy) {
        workers = filterWorkers(flatheads, requiredEnergy);
        restingWorkers = new ArrayList<>();
    }

    /**
     * get only those flatguys that fulfills requirements for worker
     *
     * @param residents      all living thins in flatland
     * @param requiredEnergy
     * @return flatguys with highest level of energy on top and with least energy level on bottom
     */
    public static PriorityQueue<Flatguy> filterWorkers(List<Flatguy> residents, int requiredEnergy) {
        PriorityQueue<Flatguy> workers = new PriorityQueue<>(Comparator.comparingInt(Flatguy::getEnergy).reversed());
        for (Flatguy f : residents) {
            if (f.getEnergy() >= requiredEnergy) {
                workers.add(f);
            }
        }
        return workers;
    }

    /**
     * @return returns currently available worker
     */
    public Flatguy get() {
        return workers.peek();
    }

    /**
     * send currently available worker to rest
     * if no worker is available just skip day
     */
    public void sendCurrToRest() {
        Flatguy curr = workers.poll();
        // when guard goes to rest it means that day passed
        if (curr != null) {
            curr.sendToRest();
            dayPassed();
            restingWorkers.add(curr);  // start counting from next day
        } else {
            dayPassed();
        }

    }

    /**
     * skip day and transfer worker from vacation to work
     */
    public void dayPassed() {
        Iterator<Flatguy> itr = restingWorkers.iterator();
        while (itr.hasNext()) {
            Flatguy curr = itr.next();
            curr.decrementRestDays();

            if (curr.getRestDaysLeft() == 0) {
                itr.remove();
                workers.add(curr);
            }
        }
    }

    /**
     * @return returns number of available workers
     */
    public int readyWorkersSize() {
        return workers.size();
    }

    /**
     * @return returns number of resting workers
     */
    public int restingWorkersSize() {
        return restingWorkers.size();
    }
}
