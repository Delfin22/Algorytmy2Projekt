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
        @Override
        public int compare(Point a, Point b) {

            double angleOfA = Math.atan2(a.getY(), a.getX());
            double angleOfB = Math.atan2(b.getY(), b.getX());

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

        minPoint.setX(0);
        minPoint.setY(0);

        //Shifting the coordinate system
        for(Point p : list){
            p.setX(p.getX() - shiftX);
            p.setY(p.getY() - shiftY);
        }

        //Sorting by angle
        Collections.sort(list, new PointAngleComparator());

        for(Point p : list){
            //System.out.println(Math.atan2(p.getY(), p.getX()));
            System.out.println(p);
        }

        return list;
    }
}
