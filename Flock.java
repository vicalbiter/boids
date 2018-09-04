import edu.princeton.cs.algs4.Vector;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;
import java.util.concurrent.LinkedBlockingQueue;

public class Flock extends Thread {
    
    private Boid[] boids;
    private LinkedBlockingQueue<Vector> predq;
    
    public Flock(Boid[] boids, LinkedBlockingQueue<Vector> predq) {
        this.boids = boids;
        this.predq = predq;
    }
    
    public void run() {
        Vector predator = new Vector(0.0, 0.0);
        while(true) {
            try {
                predator = predq.take();
                for (Boid boid : boids) {
                    boid.updatePosition();
                    boid.updateVelocity(boids, predator);
                }
            }
            catch (InterruptedException e) {
                System.out.println("Nothing there yet");
            }
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException e) {
                System.out.println("Error");
            }
        }
    }
    
    public void draw() {
        for (Boid boid : boids) {
            boid.draw();
        }
    }
}