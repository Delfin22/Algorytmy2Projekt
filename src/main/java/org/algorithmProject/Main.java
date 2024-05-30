package org.algorithmProject;
import classes.problem1.ConvexHull;
import classes.problem1.Point;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = new ArrayList<>();
        list.add(new Point(1,2));
        list.add(new Point(5,4));
        list.add(new Point(0.5d,0.5d));
        list.add(new Point(0.5d,1));
        list.add(new Point(3,0.5d));
        List<Point> list2 = ConvexHull.sortViaAngle(list);
        System.out.println(list2);
    }
}