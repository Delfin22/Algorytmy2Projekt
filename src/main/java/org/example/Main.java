package org.example;

import classes.Point;
import classes.PointGenerator;
import swing.components.FrameComponent;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Point> list = PointGenerator.createWorld(20,1920,1080);
        FrameComponent.printFrame(list);
    }
}