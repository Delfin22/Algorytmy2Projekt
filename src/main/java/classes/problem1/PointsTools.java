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

        Point minPoint = new Point();

        minPoint.setY(list.getFirst().getY());

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
            while(calculateDet(stack.get(stack.size() - 2), stack.peek(), list.get(i)) <= 0){
                stack.pop();
            }
            stack.push(list.get(i));
        }

//        for(Point p : stack){
//            System.out.println("Otoczka" + p);
//        }

        /*
        After all points are checked, the points left on the stack form the convex hull of the original list of points.
        Convert the stack to a list and return it.
        */
        list = new ArrayList<>(stack);

        return list;
    }
}
