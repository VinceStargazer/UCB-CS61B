package bearmaps;

import java.util.ArrayList;
import java.util.List;

import static bearmaps.Point.distance;

public class KDTree implements PointSet {
    private Node root;

    private static class Node implements Comparable<Node> {
        private Point point;
        private Node left, right;
        private final boolean horizontal;

        Node(Point p) {
            this(p, false);
        }

        Node(Point p, boolean h) {
            point = p;
            horizontal = h;
        }

        @Override
        public int compareTo(Node other) {
            int diff;
            if (distance(this.point, other.point) == 0) {
                diff = 0;
            } else if (horizontal) {
                diff = Double.compare(this.point.getY(), other.point.getY());
                if (diff == 0) {
                    diff--;
                }
            } else {
                diff = Double.compare(this.point.getX(), other.point.getX());
                if (diff == 0) {
                    diff--;
                }
            }
            return diff;
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            insert(p);
        }
    }

    public void insert(Point p) {
        root = insert(root, p, false);
    }

    private Node insert(Node n, Point p, boolean h) {
        if (n == null) {
            return new Node(p, h);
        }
        int cmp = n.compareTo(new Node(p));
        if (cmp > 0) {
            n.left = insert(n.left, p, !h);
        } else if (cmp < 0) {
            n.right = insert(n.right, p, !h);
        } else {
            n.point = p;
        }
        return n;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root.point);
    }

    private Point nearest(Node n, Point goal, Point best) {
        Node goodSide, badSide;
        if (n == null) {
            return best;
        }

        if (distance(goal, n.point) < distance(goal, best)) {
            best = n.point;
        }

        if (n.compareTo(new Node(goal)) > 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }

        best = nearest(goodSide, goal, best);
        Point hypo = bestPossiblePoint(n, goal);
        if (distance(goal, hypo) < distance(goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }

    private Point bestPossiblePoint(Node n, Point goal) {
        if (n.horizontal) {
            return new Point(goal.getX(), n.point.getY());
        } else {
            return new Point(n.point.getX(), goal.getY());
        }
    }

    public static void main(String[] args) {
        List<Point> lst = new ArrayList<>();
        lst.add(new Point(2, 3));
        lst.add(new Point(4, 2));
        lst.add(new Point(4, 2));
        lst.add(new Point(4, 5));
        lst.add(new Point(3, 3));
        lst.add(new Point(1, 5));
        lst.add(new Point(4, 4));
        KDTree test = new KDTree(lst);
        System.out.println("done");
    }
}
