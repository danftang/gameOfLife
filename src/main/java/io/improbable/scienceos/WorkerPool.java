package io.improbable.scienceos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by daniel on 08/02/17.
 */
public class WorkerPool {
    public ExecutorService[] poolpool;
    public ExecutorService main;
    private ThreadLocal<ExecutorService> currentExec = new ThreadLocal<>();
    // each object has a unique thread, but each thread serves many objects.


    public WorkerPool(int N) {
        int i;
        poolpool = new ExecutorService[N];
        main = Executors.newSingleThreadExecutor();
        main.execute(() -> {
            currentExec.set(main);
        });
        for(i=0; i<N; ++i) {
            poolpool[i] = Executors.newSingleThreadExecutor();
            ExecutorService p = poolpool[i];
            p.execute(() -> {
                currentExec.set(p);
            });
        }
    }

    public void shutdown() {
        int i;
        main.shutdown();
        for(i=0; i<poolpool.length; ++i) {
            poolpool[i].shutdown();
        }
    }

    public ExecutorService currentExecutor() {
        return currentExec.get();
    }
    public void setCurrentExecutor(ExecutorService t) {
        currentExec.set(t);
    }

    public ExecutorService executorFor(Object obj) {
        int poolNumber = obj.hashCode()%poolpool.length;
        return(poolpool[poolNumber]);
    }

    public ExecutorService mainExecutor() {
        return(main);
    }

}
