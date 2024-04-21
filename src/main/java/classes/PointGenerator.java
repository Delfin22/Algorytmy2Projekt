package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PointGenerator {
    public static List<Point> createWorld(int numPoints,int borderX, int borderY){
        Random random = new Random();
        Point point;
        List<Point> list = new ArrayList<>(numPoints);
        HashMap<Point,Boolean> hashMap = new HashMap<>(numPoints);

        for(int i = 0; i < numPoints; i++){
            do {
                point = new Point(random.nextDouble(borderX),random.nextDouble(borderY));
            }
            while (hashMap.containsValue(point)); //check for duplicats

            list.add(point);
            hashMap.put(point,true);
        }

            return list;
    }
}
