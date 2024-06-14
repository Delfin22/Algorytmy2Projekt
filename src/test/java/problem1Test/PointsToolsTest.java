package problem1Test;

import classes.problem1.Dijkstra;
import classes.problem1.Point;
import classes.problem1.PointsTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PointsToolsTest {
    @Test
    public void calculateDetTest(){
        Point p1 = new Point(1,2);
        Point p2 = new Point(3,4);
        Point p3 = new Point(5,6);
        Assertions.assertEquals(0, PointsTools.calculateDet(p1,p2,p3),"The determinant should be equal 0");
        p1 = new Point(3,5);
        p2 = new Point(4,1);
        p3 = new Point(12,6);
        Assertions.assertEquals(37, PointsTools.calculateDet(p1,p2,p3),"The determinant should be equal 0");
    }
    /**
     * Test case for findConvexHull function.
     * @test Tests whether the findConvexHull function returns the correct convex hull for a set of points.
     */
    @Test
    public void testFindConvexHull() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 4));

        List<Point> convexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(2, convexHull.size(), "The convex hull of four collinear points should have two points.");
        Assertions.assertTrue(convexHull.contains(new Point(1, 1)), "The convex hull should contain the point (1,1).");
        Assertions.assertTrue(convexHull.contains(new Point(4, 4)), "The convex hull should contain the point (4,4).");
    }
    @Test
    /**
     * Test case for square points.
     * @test Tests whether the findConvexHull function returns the correct convex hull for four points forming a square.
     */
    public void testSquarePoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));

        List<Point> convexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(4, convexHull.size(), "The convex hull of four points forming a square should have four points.");
        Assertions.assertTrue(convexHull.contains(new Point(0, 0)), "The convex hull should contain the point (0,0).");
        Assertions.assertTrue(convexHull.contains(new Point(0, 1)), "The convex hull should contain the point (0,1).");
        Assertions.assertTrue(convexHull.contains(new Point(1, 0)), "The convex hull should contain the point (1,0).");
        Assertions.assertTrue(convexHull.contains(new Point(1, 1)), "The convex hull should contain the point (1,1).");

        points = new ArrayList<>();
    }
    /**
     * Test case for small amount of points.
     * @test Tests whether the findConvexHull function returns the correct convex hull for a small number of points.
     * */
    @Test
    public void testSmallAmountOfPoints() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        points.add(new Point(1, 0));

        List<Point> convexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(points, convexHull, "The convex hull and points list should be the same.");

        points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        convexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(points, convexHull, "The convex hull and points list should be the same.");

        points = new ArrayList<>();
        points.add(new Point(0, 0));
        convexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(points, convexHull, "The convex hull and points list should be the same.");
    }
    /**
     * @test Tests whether the calcWallLength function returns the correct wall length for a set of points.
     */
    @Test
    public void testCalcWallLenght() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(2, 2));
        points.add(new Point(1, 2));

        Assertions.assertEquals(4, PointsTools.calcWallLenght(points), "Wall lenght should be equal to 4.");
    }
    @Test
    public void testNegativeNumbersFindConvexHull() {
        // Sample Input
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0.5, 1));
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        points.add(new Point(0, 1));
        points.add(new Point(-10, -10));

        // Expected Output
        List<Point> expectedConvexHull = new ArrayList<>();
        expectedConvexHull.add(new Point(-10, -10));
        expectedConvexHull.add(new Point(1, 0));
        expectedConvexHull.add(new Point(1, 1));
        expectedConvexHull.add(new Point(0, 1));

        List<Point> actualConvexHull = PointsTools.findConvexHull(points);

        Assertions.assertEquals(expectedConvexHull, actualConvexHull, "The convex hull is not as expected.");
    }
    @Test
    public void testGenerateRandomEdges() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 4));

        PointsTools.generateRandomEdges(points);

        for (Point point : points) {
            Assertions.assertTrue(point.getNeighbours().size() >= 2);
        }

        Point firstPoint = points.get(0);
        Point lastPoint = points.get(points.size() - 1);
        Assertions.assertTrue(firstPoint.getNeighbours().containsKey(lastPoint));
        Assertions.assertTrue(lastPoint.getNeighbours().containsKey(firstPoint));
    }
    @Test
    public void testCalcDistance() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(4, 5);

        double distance = PointsTools.calcDistance(p1, p2);

        Assertions.assertEquals(5.0, distance, "Distance between points is incorrect");
    }
    @Test
    public void testFindMinPoint() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));
        points.add(new Point(4, 4));

        Point minPoint = PointsTools.findMinPoint(points);

        Assertions.assertEquals(new Point(1, 1), minPoint, "Minimum point is incorrect");
    }
}


