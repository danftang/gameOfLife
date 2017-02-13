package io.improbable.scienceos;

import java.util.concurrent.ExecutionException;

/**
 * Created by daniel on 09/02/17.
 */
public interface ISteppable {
    void step() throws ExecutionException, InterruptedException, EndSimulationException;
}
