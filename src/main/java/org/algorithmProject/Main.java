package org.algorithmProject;

import classes.problem1.Point;
import classes.problem1.PointGenerator;
import classes.problem1.PointsTools;
import classes.problem2.Huffman;
import classes.problem2.Node;
import classes.problem3.*;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static Random rand = new Random();

    public static void main(String[] args) {

        List<Point> world = PointGenerator.createWorld(50, 800, 600);
        List<Flatguy> flatguys = Flatguy.generateLivingFlatguys(10);

        PointsTools tools = new PointsTools();
        List<Point> wall = tools.findConvexHull(world);
        System.out.println("wall:");
        wall.forEach(System.out::println);

        List<Landmark> wallWithLights = addLightsToWall(wall);
        System.out.println("After lights were added");
        wallWithLights.forEach(System.out::println);

        WorkSchedule schedule = new WorkSchedule(flatguys, wallWithLights, WorkSchedule.WEEK);
        System.out.println("\n -- SCHEDULE --");
        for (WorkDay day : schedule.getSchedule()) {
            System.out.println(day);

        }
    }

    private static List<Landmark> addLightsToWall(List<Point> wall) {
        List<Landmark> wallWithLights = new ArrayList<>();
        for (Point p : wall) {
            int brightness = rand.nextInt(Landmark.MIN_BRIGHTNESS, Landmark.MAX_BRIGHTNESS);
            wallWithLights.add(new Landmark(p, brightness));
        }

        return wallWithLights;
    }
}