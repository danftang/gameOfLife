import java.util.concurrent.CompletableFuture;

/**
 * Created by daniel on 09/02/17.
 */
public interface ISteppable {
    CompletableFuture<Void> step();
}
