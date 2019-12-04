package main;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TendencyTest {
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
        StatusCode result = systemManager.checkPreviousTenTendency();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void testNotIncreasingTendency() {
        addDatas(systemManager.getMachines().get(0), new double[] {1,2,3,3,3,4,3,3,4,3});

        StatusCode result = systemManager.checkPreviousTenTendency();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void testLastThreeIncreasing() {
        addDatas(systemManager.getMachines().get(0), new double[] {1,2,3,3,3,4,3,4,5,6});

        StatusCode result = systemManager.checkPreviousTenTendency();

        assertEquals(StatusCode.OK, result);
    }

    private void addDatas(Machine machine, double[] powerDatas) {
        for(double data: powerDatas) {
            machine.addMachineData(new MachineData(0, data));
        }
    }
}
