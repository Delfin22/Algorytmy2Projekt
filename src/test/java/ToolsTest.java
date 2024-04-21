import classes.Point;
import classes.Tools;
import org.junit.Assert;
import org.junit.Test;



public class ToolsTest {
    @Test
    public void calculateDetTest(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Point p3 = new Point(5,6);
        Assert.assertEquals(0, Tools.calculateDet(p1,p2,p3),0);

        p1 = new Point(3,5);
        p2 = new Point(4,1);
        p3 = new Point(12,6);
        Assert.assertEquals(37, Tools.calculateDet(p1,p2,p3),0);
    }
}
