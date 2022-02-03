public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;

    public Body(double xP, double yP, double xV,
                double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double xDis = b.xxPos - xxPos;
        double yDis = b.yyPos - yyPos;
        return Math.sqrt(xDis * xDis + yDis * yDis);
    }

    public double calcForceExertedBy(Body b) {
        double dis = calcDistance(b);
        return (G * mass * b.mass) / (dis * dis);
    }

    public double calcForceExertedByX(Body b) {
        double dis = calcDistance(b);
        double force = calcForceExertedBy(b);
        return force * (b.xxPos - xxPos) / dis;
    }

    public double calcForceExertedByY(Body b) {
        double dis = calcDistance(b);
        double force = calcForceExertedBy(b);
        return force * (b.yyPos - yyPos) / dis;
    }

    public double calcNetForceExertedByX(Body[] bodies) {
        Body target = bodies[0];
        double netForceX = 0;
        for (Body b : bodies) {
            if (!b.equals(this)) {
                netForceX += calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] bodies) {
        double netForceY = 0;
        for (Body b : bodies) {
            if (!b.equals(this)) {
                netForceY += calcForceExertedByY(b);
            }
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel += dt * aX;
        yyVel += dt * aY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
