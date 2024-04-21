package swing.components;
import classes.Point;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

public class PointComponent extends JComponent{
    private List<Point> list;
    public PointComponent(List<Point> list){
        super();
        this.list = list;
    }
    public void paintComponent(Graphics g){
        Integer i = 0;
        for(Point p : list) {
            System.out.println("RYSUJE NA " + p.getX() + " " + p.getY());
            g.setColor(Color.DARK_GRAY);
            g.fillOval((int) p.getX(), (int) p.getY(), 5, 5);
            g.drawString(i.toString(),(int)p.getX(),(int)p.getY()+1);
            i++;
        }
    }

}
