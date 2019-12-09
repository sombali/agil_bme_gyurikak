package main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MateTests {

    @Test
    public void checkSystemThreshold() {
        Machine m = new Machine(1);
        List<Machine> machines = new ArrayList<>();
        machines.add(m);
        SystemManager sm = new SystemManager(machines);
        assertEquals(sm.checkPowerLimit(), StatusCode.OK);
    }
}


