import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class Predator extends Thread {
    
    private Point2D position;
    private Vector vposition;
    private static double X_LIMIT = 5.0;
    private static double Y_LIMIT = 5.0;
    private LinkedBlockingQueue<Vector> predq;
    
    public Predator(double x, double y, LinkedBlockingQueue<Vector> predq) {
        position = new Point2D(x, y);
        this.predq = predq;
    }
    
    public Point2D position() {
        return position;
    }
    
    public Point2D setDestination() {
        double x = StdRandom.uniform(-0.5*X_LIMIT, 0.5*X_LIMIT);
        double y = StdRandom.uniform(-0.5*Y_LIMIT, 0.5*Y_LIMIT);
        return new Point2D(x, y);
    }
    
    public double setSpeed() {
        return StdRandom.uniform(0.05, 0.1);
    }
    
    public void updatePosition(Vector velocity) {
        double x = position.x() + velocity.cartesian(0);
        double y = position.y() + velocity.cartesian(1);
        position = new Point2D(x, y);
    }
    
    public void run() {
        while (true) {
            Point2D destination = setDestination();
            double speed = setSpeed();
            Vector velocity = new Vector(destination.x() - position().x(), destination.y() - position().y());
            velocity = velocity.direction().scale(speed);
            while (position.distanceTo(destination) > 0.2) {
                updatePosition(velocity);
                try {
                    predq.put(new Vector(position.x(), position.y()));
                    Thread.sleep(20);
                }
                catch (InterruptedException e) {
                    System.out.println("Error");
                }
            }
        }
    }
    
    public void draw() {
        position.draw();
    }
    
}