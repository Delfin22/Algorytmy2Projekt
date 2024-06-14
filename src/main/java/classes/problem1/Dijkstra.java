package classes.problem1;
import java.util.*;

public class Dijkstra {
    /**
     * Finds the shortest path between the source point and the target point in a graph.
     * @param source The starting point for the path.
     * @param target The ending point for the path.
     * @param points A list of all points in the graph.
     * @return An ordered list of points representing the shortest path from the source to the target.
     */
    public static ArrayList<Point> shortestPath(Point source, Point target, List<Point> points) {
        // priority queue with a comparator that sorts points based on their distance from the source.
        PriorityQueue<Point> queue = new PriorityQueue<>(Comparator.comparingDouble(Point::getDistance));
        // map to keep track of the previous point on the shortest path for each point.
        Map<Point, Point> previousNode = new HashMap<>();
        source.setDistance(0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(target)) {
                break;
            }
            // For each neighbour of the current point, calculate the distance through the current point.
            for (Map.Entry<Point, Double> entry : current.getNeighbours().entrySet()) {
                Point neighbour = entry.getKey();
                double weight = entry.getValue();
                double distanceThroughCurrent = current.getDistance() + weight;

                // If the distance through the current point is shorter than the current distance, update the distance and the previous point
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
