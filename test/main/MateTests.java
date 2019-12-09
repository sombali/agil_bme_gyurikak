package main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class MateTests {
    private  SystemManager sm;

    @Before
    public void setup() {
        sm = new SystemManager(new ArrayList<Machine>());
    }

    @Test
    public void checkSystemThresholdLow() {
        ArrayList<Machine> machines =  new ArrayList<Machine>();
        machines.add(new Machine(1));
        SystemManager local_sm = new SystemManager(machines);
        assertEquals(local_sm.checkPowerLimit(), StatusCode.OK);
    }

    @Test
    public void checkSystemThresholdHigh() {
        ArrayList<Machine> machines =  new ArrayList<Machine>();
        machines.add(new Machine(100));
        SystemManager local_sm = new SystemManager(machines);
        assertEquals(local_sm.checkPowerLimit(), StatusCode.ERROR);
    }

    @Test
    public void checkSystemThresholdOnTreshold() {
        ArrayList<Machine> machines =  new ArrayList<Machine>();
        machines.add(new Machine(10));
        SystemManager local_sm = new SystemManager(machines);
        assertEquals(local_sm.checkPowerLimit(), StatusCode.WARNING);
    }

    @Test
    public void addMachine() {
        int initialMachineCnt = this.sm.getMachines().size();
        Machine m1 = new Machine(1);
        StatusCode result = this.sm.addMachine(m1);
        assertEquals(result, StatusCode.OK);
        assertEquals(initialMachineCnt + 1, this.sm.getMachines().size());
    }

    @Test
    public void addMachineOnTreshold() {
        int initialMachineCnt = this.sm.getMachines().size();
        Machine m1 = new Machine(10);
        StatusCode result = this.sm.addMachine(m1);
        assertEquals(result, StatusCode.OK);
        assertEquals(initialMachineCnt + 1, this.sm.getMachines().size());
    }

}


