package io.improbable.scienceos.test;

import io.improbable.scienceos.*;
import org.junit.Before;
import org.junit.Test;

public class TestGameOfLife {

    public Conductor conductor;

    @Before
    public void setUp() {
        this.conductor = new Conductor(new Simulation());
    }

    @Test
    public void testConductorRuns() {
        this.conductor.run();
    }

}
