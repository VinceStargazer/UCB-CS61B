package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private final List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point best = points.get(0);
        for (Point p : points) {
            if (Point.distance(p, goal) < Point.distance(best, goal)) {
                best = p;
            }
        }
        return best;
    }
}
