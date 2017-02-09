import java.util.concurrent.CompletableFuture;

/**
 * Created by daniel on 08/02/17.
 */
public class SimulationReference extends Reference<Simulation> {
    public SimulationReference(Simulation referent) {
        super(referent);
    }

    CompletableFuture<Void> step() {
        return CompletableFuture.runAsync(() -> referent.step(), executor).thenRunAsync(() ->{}, PoolPool.currentExecutor());
    }

}
