import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Vector;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;

public class Boid {
    
    private Point2D point;
    private Vector position;
    private Vector velocity;
    private double x, y;
    private double C_HOME = 0.1;
    private double C_SEPARATION = 0.1;
    private double C_ALIGNMENT = 0.5;
    private double C_COHESION = 0.5;
    private double C_MAGNITUDE = 0.001;
    private double C_DISTANCE = 0.5;
    private double C_PREDATOR = 0.07;
    
    public Boid(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
        this.x = position.cartesian(0);
        this.y = position.cartesian(1);
        point = new Point2D(x, y);
    }
    
    public Point2D point() {
        return point;
    }
    
    public Vector position() {
        return position;
    }
    
    public Vector velocity() {
        return velocity;
    }
    
    public Vector calculateNewVelocity() {
        return null;
    }
    
    public void updatePosition() {
        position = position.plus(velocity);
        x = position.cartesian(0);
        y = position.cartesian(1);
        point = new Point2D(x, y);
    }
    
    public Stack<Boid> findNearest(Boid[] neighbors) {
        Stack<Boid> nearest = new Stack<Boid>();
        for (Boid neighbor : neighbors) {
            if (point.distanceTo(neighbor.point()) < C_DISTANCE) {
                nearest.push(neighbor);
            }
        }
        return nearest;
    }

    public void updateVelocity(Boid[] neighbors, Vector predator) {
        Stack<Boid> nearest = findNearest(neighbors);
        Vector separation = calculateSeparationVector(nearest).scale(C_SEPARATION);
        Vector alignment = calculateAlignmentVector(nearest).scale(C_ALIGNMENT);
        Vector cohesion = calculateCohesionVector(nearest).scale(C_COHESION);
        Vector home = calculateHomeVector().scale(C_HOME);
        Vector pred = calculatePredatorVector(predator).scale(C_PREDATOR);
        Vector velchange = new Vector(0.0, 0.0);
        velchange = velchange.plus(separation);
        velchange = velchange.plus(alignment);
        velchange = velchange.plus(cohesion);
        velchange = velchange.plus(home);
        velchange = velchange.plus(pred);
        velchange = velchange.direction().scale(C_MAGNITUDE);
        velocity = velocity.plus(velchange);
    }
    
    private Vector calculateSeparationVector(Iterable<Boid> neighbors) {
        Vector separation = new Vector(0.0, 0.0);
        for (Boid neighbor : neighbors) {
            if (neighbor.x == x && neighbor.y == y) {
                continue;
            }
            double distance = point.distanceTo(neighbor.point());
            Vector vector = position.minus(neighbor.position());
            Vector scaled = vector.scale(0.5/distance);
            separation = separation.plus(scaled);
        }
        return separation;
    }
    
    private Vector calculateAlignmentVector(Iterable<Boid> neighbors) {
        Vector alignment = new Vector(0.0, 0.0);
        for (Boid neighbor : neighbors) {
            if (neighbor.x == x && neighbor.y == y) {
                continue;
            }
            Vector vector = neighbor.velocity().minus(velocity);
            alignment = alignment.plus(vector);
        }
        return alignment;
    }
    
    private Vector calculateCohesionVector(Iterable<Boid> neighbors) {
        Vector cohesion = new Vector(0.0, 0.0);
        Vector centroid = new Vector(0.0, 0.0);
        int n = 0;
        for (Boid neighbor : neighbors) {
            if (neighbor.x == x && neighbor.y == y) {
                continue;
            }
            centroid = centroid.plus(neighbor.position());
            n++;
        }
        if (n != 0) {
            centroid = centroid.scale(1.0 / n);
            cohesion = centroid.minus(position);
        }
        return cohesion;
    }
    
    private Vector calculateHomeVector() {
        Vector home = new Vector(0.0, 0.0);
        home = home.minus(position);
        return home;
    }
    
    private Vector calculatePredatorVector(Vector predator) {
        return position.minus(predator);
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }
    
    public static void main(String[] args) {
        
    }

}