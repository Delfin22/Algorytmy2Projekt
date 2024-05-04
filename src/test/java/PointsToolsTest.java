import classes.problem1.Point;
import classes.problem1.PointsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PointsToolsTest {
    @Test
    public void calculateDetTest(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Point p3 = new Point(5,6);
        Assertions.assertEquals(0, PointsTools.calculateDet(p1,p2,p3),0);
        p1 = new Point(3,5);
        p2 = new Point(4,1);
        p3 = new Point(12,6);
        Assertions.assertEquals(37, PointsTools.calculateDet(p1,p2,p3),0);
    }
}
