package classes.problem1;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Point> sortViaDegree(List<Point> list){
        List<Point> returnList = new ArrayList<>();

        Point minPoint = new Point();

        minPoint.setY(list.get(0).getY());

        //Looking for s point with lowest Y value
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

        System.out.println(minPoint);
        System.out.println(shiftX + " " + shiftY);

        System.out.println("After shift....");

        for(Point p : list){
            System.out.println(p);
        }

        return list;
    }
    public static List<Point> createConvexHull(List <Point> list){
        List<Point> returnList = new ArrayList<>();

        return returnList;
    }
}
