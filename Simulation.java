import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class Simulation {
    
    private static double X_LIMIT = 5.0;
    private static double Y_LIMIT = 5.0;

    public static void main(String[] args) {
        LinkedBlockingQueue<Vector> predq = new LinkedBlockingQueue<Vector>(1);
        StdDraw.setPenRadius(0.005);
        StdDraw.setXscale(-2*X_LIMIT, X_LIMIT*2);
        StdDraw.setYscale(-2*Y_LIMIT, Y_LIMIT*2);
        StdDraw.enableDoubleBuffering(); 
        Predator predator = new Predator(0.0, 0.0, predq);
        predator.start();
        TestBoid test = new TestBoid();
        Flock flock = new Flock(test.randomBoids(1000), predq);
        flock.start();
        StdDraw.enableDoubleBuffering();
        while(true) {
            StdDraw.clear();
            predator.draw();
            flock.draw();
            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}