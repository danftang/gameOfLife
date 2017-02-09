import com.sun.org.apache.xpath.internal.axes.FilterExprIteratorSimple;

import javax.naming.ReferralException;
import java.sql.Ref;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
        Reference.pool.setCurrentExecutor(Reference.pool.main);
        ISimulation sim = new Simulation();
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
