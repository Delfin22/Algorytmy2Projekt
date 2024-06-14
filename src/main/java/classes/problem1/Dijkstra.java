package classes.problem1;
import java.util.*;

public class Dijkstra {
    public static ArrayList<Point> shortestPath(Point source, Point target, List<Point> points) {
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(Point::getDistance));
        Map<Point, Point> previousNode = new HashMap<>();
        source.setDistance(0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(target)) {
                break;
            }

            for (Map.Entry<Point, Double> entry : current.getNeighbours().entrySet()) {
                Point neighbour = entry.getKey();
                double weight = entry.getValue();
                double distanceThroughCurrent = current.getDistance() + weight;

                if (distanceThroughCurrent < neighbour.getDistance()) {
                    neighbour.setDistance(distanceThroughCurrent);
                    previousNode.put(neighbour, current);
                    if(queue.contains(neighbour)){
                        queue.remove(neighbour);
                    }
                    queue.add(neighbour);
                }
            }
        }

        ArrayList<Point> path = new ArrayList<>();
        for (Point at = target; at != null; at = previousNode.get(at)) {
            path.add(at);
        }

        Collections.reverse(path);
        return path;
    }
}
