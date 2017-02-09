import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by daniel on 08/02/17.
 */
public class Simulation implements ISimulation {
    final int nTiles=6;
    final int tileSize = 100;
    TileReference [] tilerefs = new TileReference[nTiles];

    public Simulation() {
        int i;
        for(i=0; i<nTiles; ++i) {
            tilerefs[i] = new TileReference(tileSize);
        }
        Tile.join(tilerefs);
    }

    public void step() throws ExecutionException, InterruptedException, EndSimulationException {
        CompletableFuture<Void>[] steps = new CompletableFuture[nTiles];
        for(int i=0; i<nTiles; ++i) {
            steps[i] = tilerefs[i].step();
        }
        CompletableFuture.allOf(steps).get();
        CompletableFuture<Void>[] updates = new CompletableFuture[nTiles];
        for(int i=0; i<nTiles; ++i) {
            updates[i] = tilerefs[i].update();
        }
        CompletableFuture.allOf(updates).get();
        printState();
    }

    public void printState() throws ExecutionException, InterruptedException {
        for(int n=0; n<nTiles; ++n) {
            printState(n).get();
        }
        System.out.println("");
    }

    public CompletableFuture<Void> printState(int n) {
        return tilerefs[n].printState(n*tileSize, 0);
    }

    @Override
    public void stop() {

    }
}
