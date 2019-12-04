package main;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class BalazsTests {

    private SystemManager systemManager;

    @Before
    public void setUp() {
        Machine machine = new Machine(10,10);
        List<Machine> machines = new ArrayList<>();
        machines.add(machine);

        systemManager = new SystemManager(machines);
    }

    @Test
    public void testEmptyPrevious10() {
        StatusCode result = systemManager.checkIfHasZero();

        assertEquals(result, StatusCode.OK);
    }

    @Test
    public void testOneGoodElementInPreviousTen() {
        systemManager.getMachines().get(0).addMachineData(new MachineData(10,10));

        StatusCode result = systemManager.checkIfHasZero();

        assertEquals(result, StatusCode.OK);
    }

    @Test
    public void testOneBadElementInPreviousTen() {
        systemManager.getMachines().get(0).addMachineData(new MachineData(10,0));

        StatusCode result = systemManager.checkIfHasZero();

        assertEquals(result, StatusCode.WARNING);
    }

}
