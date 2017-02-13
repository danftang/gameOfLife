package io.improbable.scienceos;

import java.util.concurrent.ExecutorService;

/**
 * Created by daniel on 08/02/17.
 */
public class Reference<T> {
    public static WorkerPool pool = new WorkerPool(4);
    public ExecutorService executor;
    public T               referent;

    public Reference(T referent) {
        executor = pool.executorFor(referent);
        this.referent = referent;
    }

}
