package swing.components;

import classes.Point;

import javax.swing.*;
import java.util.List;

public class FrameComponent extends JFrame {
    private static FrameComponent frame = new FrameComponent();
    public static void printFrame(List<Point> points){
        frame.setSize(1920,1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        printPoints(points);
    }
    private static void printPoints(List<Point> list){
        PointComponent pointComponent = new PointComponent(list);
            frame.add(pointComponent);
        }
    }

