package classes.problem1;

import java.util.*;

public class PointsTools {
    public static double calculateDet(Point p1, Point p2, Point p3){
        double result;

//        p1.x p1.y 1
//        p2.x p2.y 1
//        p3.x p3.y 1

        result = p1.getX()*p2.getY() +
                p2.getX()*p3.getY() +
                p3.getX()*p1.getY() -
                p2.getY()*p3.getX() -
                p3.getY()*p1.getX() -
                p1.getY()*p2.getX();

        return result;
    }

    static class PointAngleComparator implements Comparator<Point> {

        Point minPoint;

        public PointAngleComparator(Point minPoint) {
            this.minPoint = minPoint;
        }

        @Override
        public int compare(Point a, Point b) {

            double angleOfA = Math.atan2(a.getY() - minPoint.getY(), a.getX() - minPoint.getX());
            double angleOfB = Math.atan2(b.getY() - minPoint.getY(), b.getX() - minPoint.getX());

            return angleOfA < angleOfB ? -1 : 1;
        }
    }

    public static List<Point> findConvexHull(List<Point> list){
        List<Point> returnList = new ArrayList<>();

        Point minPoint = new Point();

        minPoint.setY(list.getFirst().getY());

        //Looking for a point with the lowest Y value
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

        double shiftX = minPoint.getX(), shiftY = minPoint.getY();

        System.out.println(minPoint + "Minmalny");

//        minPoint.setX(0);
//        minPoint.setY(0);
//
//        //Shifting the coordinate system
//        for(Point p : list){
//            p.setX(p.getX() - shiftX);
//            p.setY(p.getY() - shiftY);
//        }

        //Sorting by angle
        Collections.sort(list, new PointAngleComparator(minPoint));

//        for(Point p : list){
//            System.out.println("After shift\n");
//            System.out.println(Math.atan2(p.getY(), p.getX()));
//            System.out.println(p);
//        }

        Stack<Point> stack = new Stack<>();

        stack.push(list.get(0));
        stack.push(list.get(1));
        stack.push(list.get(2));

        for(int i = 3; i < list.size(); i++){
            while(calculateDet(stack.get(stack.size() - 2), stack.peek(), list.get(i)) <= 0){
                stack.pop();
            }
            stack.push(list.get(i));
        }

        for(Point p : stack){
            System.out.println("Otoczka" + p);
        }
        return list;
    }

//    Outated code, might be useful
//    public static boolean isWithinTriangle (Point a, Point b, Point c, Point x){
//
//        //ABC triangle area
//        double A1 = calcTriangleArea(a, b, c);
//        //XBC triangle area
//        double A2 = calcTriangleArea(x, b, c);
//        //XAC triangle area
//        double A3 = calcTriangleArea(a, x, c);
//        //XAB triangle area
//        double A4 = calcTriangleArea(a, b, x);
//
//        return (A1 == A2 + A3 + A4);
//    }

//    public static double calcTriangleArea (Point a, Point b, Point c){
//        return Math.abs(a.getX()*(b.getY() - c.getY()) +
//                        b.getX()*(c.getY() - a.getY()) +
//                        c.getX()*(a.getY() - b.getY()))/2;
//    }
}
