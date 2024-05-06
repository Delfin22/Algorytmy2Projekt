package org.algorithmProject;
import classes.problem1.Point;
import classes.problem1.PointGenerator;
import classes.problem1.PointsTools;
import classes.problem2.PatternTools;
import swing.components.FrameComponent;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = PointGenerator.createWorld(20,1920,1080);
        FrameComponent.printFrame(list);
        for(Point p : list){
            System.out.println(p);
        }
        PointsTools.sortViaDegree(list);


        String text = "kjjnbo limodsarfizmasmlkd boliassabolisad";
        String pattern = "boli";
        System.out.println(PatternTools.patternSearchKR(text,pattern));
        System.out.println(PatternTools.patternSearchBM(text,pattern));

    }
}