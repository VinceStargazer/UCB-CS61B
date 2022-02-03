public class NBody {
    public static double readRadius(String file) {
        In in = new In(file);
        int num = in.readInt();
        return in.readDouble();
    }

    public static Body[] readBodies(String file) {
        In in = new In(file);
        int i = 0;
        int num = in.readInt();
        double radius = in.readDouble();
        Body[] bodies = new Body[num];
        while (i < num) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            Body b = new Body(xP, yP, xV, yV, m, img);
            bodies[i] = b;
            i++;
        }
        return bodies;
    }

    public static void drawBackground(double radius) {
        String pic = "images/starfield.jpg";
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, pic);
    }

    public static void main(String[] args) {
        double time = 0;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        StdDraw.enableDoubleBuffering();
        while (time <= T) {
            int i = 0;
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            while (i < bodies.length) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                i++;
            }
            for (int j = 0; j < bodies.length; j++) {
                bodies[j].update(dt, xForces[j], yForces[j]);
            }
            drawBackground(radius);
            for (Body b : bodies) {
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (Body body : bodies) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    body.xxPos, body.yyPos, body.xxVel,
                    body.yyVel, body.mass, body.imgFileName);
        }
    }
}
