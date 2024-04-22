package classes;

import java.util.ArrayList;
import java.util.List;

public class Tools {
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
    private static List<Double> sortViaDegree(List<Double> list){
        return list;
    }
    public static List<Double> createConvexHull(List <Double> list){
        List<Double> returnList = new ArrayList<>();
        return returnList;
    }
}
