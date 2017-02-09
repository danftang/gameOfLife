import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class Main {
    public static Simulation sim = new Simulation();

    public static void main(String[] args) {
        try {
            int i,p;
            for(i=0; i<400; ++i) {
                Reference.pool.mainExecutor().submit(() -> {
                    sim.step();
                }).get();
                sim.printState();
                System.out.println("");
                System.out.println("");
            }
            sleep(200);
            Reference.pool.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
