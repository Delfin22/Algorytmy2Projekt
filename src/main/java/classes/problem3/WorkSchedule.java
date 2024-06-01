package classes.problem3;

import classes.problem1.Point;

import java.util.*;

public class WorkSchedule {
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
            List<StopPoint> path = prepareOptimalPath(worker);

            schedule[i] = new WorkDay(worker, i, path);
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

    public List<StopPoint> prepareOptimalPath(Flatguy guard) {
        List<StopPoint> path = new ArrayList<>();

        Landmark startPoint = Collections.max(wall, (p1, p2) -> Integer.compare(p1.getBrightness(), p2.getBrightness()));
        path.add(new StopPoint(startPoint, false));

        Landmark next = getNextDarkerLandmark(path, guard.getEnergy());
        while (next != null) {
            boolean restRequired = path.getLast().landmark().getBrightness() < next.getBrightness();
            path.add(new StopPoint(next, restRequired));

            boolean returnToStart = path.getFirst().landmark().equals(path.getLast().landmark());
            if (returnToStart) {
                break;
            }

            next = getNextDarkerLandmark(path, guard.getEnergy());
        }

        return path;
        // TODO: returns optimal starting point and prepares path
        // TODO: find optimal starting point based on energy of worker
    }


    private Landmark getNextDarkerLandmark(List<StopPoint> path, int energy) {
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
        return darkest  == null ? current : darkest;
    }

    private void run() {
        for (WorkDay day : schedule) {
            System.out.printf("Flatguy %d energi\n", day.guard.getEnergy());
            day.printPath();

            day.guard.sendToRest();
        }
    }
}