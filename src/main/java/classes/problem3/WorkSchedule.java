package classes.problem3;

import classes.problem1.Point;

import java.util.*;

public class WorkSchedule {
    static final Random rand = new Random();
    static final int FLATGUYS_NUMBER = 10;
    static final int REQUIRED_ENERGY = 1;  // by default allow all to work
    static final int WEEK = 7;
    final List<Flatguy> workers;
    final List<Landmark> wall;

    final WorkDay[] schedule;


    public WorkSchedule(List<Flatguy> residents, List<Point> pointsWall) {
        // wall = addLightsToWall(pointsWall);
        wall = getWall();
        // filter only that flatguy's that fulfill requirements
        workers = residents.stream()
                .filter(r -> r.getEnergy() >= REQUIRED_ENERGY)
                .sorted((r1, r2) -> Integer.compare(r2.getEnergy(), r1.getEnergy()))
                .limit(WEEK)
                .toList();

        schedule = new WorkDay[WEEK];
        for (int i = 0; i < schedule.length; ++i) {
            Flatguy worker = workers.get(i);
            worker.prepareOptimalPath(wall);

            schedule[i] = new WorkDay(worker, i);
        }
    }

    public static void main(String[] args) {
        // we already have wall -> list of points
        List<Point> points_wall = new ArrayList<>();
        List<Flatguy> residents = generateLivingFlatguys();

        WorkSchedule schedule = new WorkSchedule(residents, points_wall);

        schedule.run();
    }

    public static List<Flatguy> generateLivingFlatguys() {
        List<Flatguy> residents = new ArrayList<>();
        for (int i = 0; i < FLATGUYS_NUMBER; ++i) {
            int energy = rand.nextInt(Flatguy.MIN_ENERGY, Flatguy.MAX_ENERGY);
            residents.add(new Flatguy(energy));

        }

        return residents;
    }

    private List<Landmark> getWall() {
        final List<Landmark> wall = new ArrayList<>();
        wall.add(new Landmark(new Point(300, 100), 5));
        wall.add(new Landmark(new Point(400, 100), 8));
        wall.add(new Landmark(new Point(500, 100), 1));
        wall.add(new Landmark(new Point(600, 200), 2));
        wall.add(new Landmark(new Point(700, 300), 4));
        wall.add(new Landmark(new Point(700, 400), 9));
        wall.add(new Landmark(new Point(700, 500), 10));
        wall.add(new Landmark(new Point(600, 600), 2));
        wall.add(new Landmark(new Point(500, 700), 6));
        wall.add(new Landmark(new Point(400, 700), 8));
        wall.add(new Landmark(new Point(300, 700), 9));  // 10 index for 9 brightness
        wall.add(new Landmark(new Point(200, 600), 3));
        wall.add(new Landmark(new Point(100, 500), 3));
        wall.add(new Landmark(new Point(100, 400), 4));
        wall.add(new Landmark(new Point(100, 300), 7));
        wall.add(new Landmark(new Point(200, 200), 8));

        return wall;
    }

    private List<Landmark> addLightsToWall(List<Point> points_wall) {
        final List<Landmark> wall = new ArrayList<>();
        for (Point p : points_wall) {
            int brightness = rand.nextInt(1, 10);
            wall.add(new Landmark(p, brightness));
        }
        return wall;
    }

    private void run() {
        System.out.println("workers");
        for (int i = 0; i < workers.size(); ++i) {
            Flatguy w = workers.get(i);

            System.out.printf("plaszczak %d -> %d energii\n", i, w.getEnergy());
            w.printPath();
            w.sendToRest();
        }
    }
}