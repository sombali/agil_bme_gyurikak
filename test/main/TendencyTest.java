package main;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TendencyTest {

    @Test
    public void testEmptyPrevious10() {
        Machine machine = new Machine(10,10);
        List<Machine> machines = new ArrayList<>();
        machines.add(machine);

        SystemManager systemManager = new SystemManager(machines);
        StatusCode result = systemManager.checkPreviousTenTendency();

        assertEquals(result, StatusCode.OK);
    }
}
