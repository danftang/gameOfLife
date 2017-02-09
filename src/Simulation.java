import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by daniel on 08/02/17.
 */
public class Simulation implements ISteppable {
    final int nTiles=6;
    Tile [] tiles = new Tile[nTiles];
    TileReference [] tilerefs = new TileReference[nTiles];

    public Simulation() {
        int i;
        for(i=0; i<nTiles; ++i) {
            tiles[i] = new Tile(100);
            tilerefs[i] = new TileReference(tiles[i]);
        }
        Tile myTile = tiles[0];
        TileReference myRef = new TileReference(myTile);

        myTile.step();
        myRef.step().thenAccept((result) -> {
            // do other stuff
        });


        Tile.join(tiles);
    }

    public CompletableFuture<Void> step() {
            CompletableFuture<Void>[] steps = new CompletableFuture[nTiles];
            for(int i=0; i<nTiles; ++i) {
                steps[i] = tilerefs[i].step();
            }
        return CompletableFuture.allOf(steps).thenCompose((dummy) -> {
                    CompletableFuture<Void>[] updates = new CompletableFuture[nTiles];
                    for(int i=0; i<nTiles; ++i) {
                        updates[i] = tilerefs[i].update();
                    }
                    return CompletableFuture.allOf(updates);
                }
        );
    }

    public void printState() {
        int n;
        int xOrigin = 0;
        for(n=0; n<nTiles; ++n) {
            tiles[n].printState(xOrigin, 0);
            xOrigin += tiles[n].state.length-2;
        }
    }

}
