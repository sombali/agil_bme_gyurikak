package main;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BalazsTests {

    @Test
    public void testEmptyPrevious10() {
        Machine machine = new Machine(10,10);
        List<Machine> machines = new ArrayList<>();
        machines.add(machine);

        SystemManager systemManager = new SystemManager(machines);

        StatusCode result = systemManager.checkIfHasZero();

        assertEquals(result, StatusCode.OK);
    }

}
