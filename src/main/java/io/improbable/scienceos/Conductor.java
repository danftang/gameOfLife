package io.improbable.scienceos;

import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class Conductor implements Runnable {

    private ISimulation sim;

    static {
        Reference.pool.setCurrentExecutor(Reference.pool.main);
    }

    public Conductor(ISimulation sim) {
        this.sim = sim;
    }

    public void run() {
        try {
            int i;
            for(i=0; i<100; ++i) {
                sim.step();
            }
            sleep(200);
            sim.stop();
            Reference.pool.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (EndSimulationException e) {
            sim.stop();
            Reference.pool.shutdown();
        }
    }
}
