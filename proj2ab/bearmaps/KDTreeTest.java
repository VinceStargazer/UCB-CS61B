package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static bearmaps.ArrayHeapMinPQTest.timerPrint;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private static final Random R = new Random(500);

    private static KDTree buildLectureTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        return new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
    }

    @Test
    public void testNearest() {
        KDTree kd = buildLectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }

    private Point randomPoint() {
        double x = R.nextDouble();
        double y = R.nextDouble();
        return new Point(x, y);
    }

    /** Return N random points */
    private List<Point> randomPoints(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPoint());
        }
        return points;
    }

    private void testWithPointsAndQueries(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPoints(queryCount);
        for (Point p : queries) {
            Point actual = nps.nearest(p.getX(), p.getY());
            Point expected = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private void testRunningTime(int pointCount, int queryCount) {
        List<Point> points = randomPoints(pointCount);
        Stopwatch sw = new Stopwatch();
        KDTree kd = new KDTree(points);
        timerPrint("KDTree adds " + pointCount + " items: ", sw, false);

        sw = new Stopwatch();
        NaivePointSet nps = new NaivePointSet(points);
        timerPrint("NPS adds " + pointCount + " items: ", sw, true);

        List<Point> queries = randomPoints(queryCount);
        sw = new Stopwatch();
        for (Point p : queries) {
            kd.nearest(p.getX(), p.getY());
        }
        timerPrint("KDTree runs nearest " + queryCount + " times: ", sw, false);

        sw = new Stopwatch();
        for (Point p : queries) {
            nps.nearest(p.getX(), p.getY());
        }
        timerPrint("NPS runs nearest " + queryCount + " times: ", sw, true);
    }

    @Test
    public void testWith1000PointsAnd200Queries() {
        testWithPointsAndQueries(1000, 200);
    }

    @Test
    public void testWith10000PointsAnd2000Queries() {
        testWithPointsAndQueries(10000, 2000);
    }

    @Test
    public void testSpeedWith100000PointsAnd10000Queries() {
        testRunningTime(100000, 10000);
    }
}
