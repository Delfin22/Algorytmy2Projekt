package org.algorithmProject;

import classes.Point;
import classes.PointGenerator;
import classes.Tools;
import swing.components.FrameComponent;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = PointGenerator.createWorld(20,1920,1080);
        FrameComponent.printFrame(list);
        for(Point p : list){
            System.out.println(p);
        }
        Tools.sortViaDegree(list);
    }
}