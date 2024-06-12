package classes.problem1;

import java.util.*;

public class PointsTools {
    /**
     * Calculates the determinant of three points.
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @param p3 The third point.
     * @return The determinant of the three points.
     */
    public static double calculateDet(Point p1, Point p2, Point p3){
        double result;
        /*
         This formula is equivalent to the determinant of the following 3x3 matrix:

         | p1.getX()  p1.getY()  1 |
         | p2.getX()  p2.getY()  1 |
         | p3.getX()  p3.getY()  1 |
        */
        result = p1.getX() * p2.getY() +
                p2.getX() * p3.getY() +
                p3.getX() * p1.getY() -
                p2.getY() * p3.getX() -
                p3.getY() * p1.getX() -
                p1.getY() * p2.getX();

        return result;
    }

    /**
     * A comparator for comparing the angles of two points with respect to a minimum point.
     */
    static class PointAngleComparator implements Comparator<Point> {

        Point minPoint;
        /**
         * Constructs a new PointAngleComparator with the specified minimum point.
         *
         * @param minPoint The point with the minimum y-coordinate.
         */
        public PointAngleComparator(Point minPoint) {
            this.minPoint = minPoint;
        }
        /**
         * Compares the angles of two points with respect to the minimum point.
         *
         * @param a The first point.
         * @param b The second point.
         * @return -1 if the angle of point a is less than the angle of point b, 1 otherwise.
         */
        @Override
        public int compare(Point a, Point b) {

            double angleOfA = Math.atan2(a.getY() - minPoint.getY(), a.getX() - minPoint.getX());
            double angleOfB = Math.atan2(b.getY() - minPoint.getY(), b.getX() - minPoint.getX());

            return angleOfA < angleOfB ? -1 : 1;
        }
    }
    /**
     * Implementation of Graham's algorithm to find the convex hull of a list of points.
     * Time complexity O(n log n)
     *
     * @param list The list of points.
     * @return The list of points forming the convex hull.
     */
    public static List<Point> findConvexHull(List<Point> list){

        // If there is only between 1 and 3 points then those points form the convex hull.
        if(list.size() < 4 && list.size() > 0)
            return list;

        Point minPoint = new Point();

        minPoint.setY(list.getFirst().getY());
        minPoint.setX(list.getFirst().getX());

        // Loop over the list of points to find the point with the minimum y-coordinate.
        for(Point p : list){
            if(p.getY() < minPoint.getY()){
                minPoint.setY(p.getY());
                minPoint.setX(p.getX());
            }else if(p.getY() == minPoint.getY()) {
                if(p.getX() < minPoint.getX()){
                    minPoint.setY(p.getY());
                    minPoint.setX(p.getX());
                }
            }
        }

        // Sort the list of points based on the angle they make with minPoint.
        Collections.sort(list, new PointAngleComparator(minPoint));

        // Initialize a stack and push the first three points in the sorted list onto the stack.
        Stack<Point> stack = new Stack<>();

        stack.push(list.get(0));
        stack.push(list.get(1));
        stack.push(list.get(2));

        /*
         Loop over the sorted list.
         While the angle created by the points second-to-top, top point and the current point
         dont form a left turn, pop the top point from the stack.
        */
        for(int i = 3; i < list.size(); i++){
            while(stack.size() > 1 && calculateDet(stack.get(stack.size() - 2), stack.peek(), list.get(i)) <= 0){
                stack.pop();
            }
            stack.push(list.get(i));
        }

        /*
         After all points are checked, the points left on the stack form the convex hull of the original list of points.
         Convert the stack to a list and return it.
        */
        list = new ArrayList<>(stack);

        return list;
    }

    /**
     * Calculates the Euclidean distance between two points.
     * This method calculates the distance between two points using the formula:
     * d = sqrt((x2 - x1)^2 + (y2 - y1)^2)
     *
     * @param p1 The first point.
     * @param p2 The second point.
     * @return Distance between the points.
     */
    public static double calcDistance(Point p1, Point p2){
        double v1 = p2.getX() - p1.getX();
        double v2 = p2.getY() - p1.getY();
        return Math.sqrt(v1 * v1 + v2 * v2);
    }

    /**
     * Calculates the total length of the convex hull formed by a list of points.
     * The length of each wall is the Euclidean distance between two neighbouring points in the list.
     * The total length of the convex hull is the sum of the lengths of all the walls.
     *
     * @param list The list of points.
     * @return The total length of the convex hull.
     */
    public static double calcWallLenght(List<Point> list){

        double totalWallLenght = 0;

        if(list.isEmpty() || list.size() == 1)
            return 0;
        if(list.size() == 2){
                totalWallLenght = calcDistance(list.getFirst(), list.getLast());
                return  totalWallLenght;
        }

        /*
         For each point, calculate the distance to the next point and add it to the total length.
         If the current point is the last point in the list, calculate the distance to the first point instead.
        */
        for(int i = 0; i < list.size(); i++)
            totalWallLenght += calcDistance(list.get(i), list.get((i + 1 == list.size()) ? 0 : i + 1));

        return totalWallLenght;
    }
}
