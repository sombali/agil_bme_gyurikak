package main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class MateTests {
    private  SystemManager sm1;
    private  SystemManager sm2;

    @Before
    public void setup() {
        sm1 = new SystemManager(new ArrayList<Machine>());
        sm2 = new SystemManager(new ArrayList<Machine>());
    }

    @Test
    public void checkSystemThresholdLow() {
        Machine m = new Machine(1);
        sm1.addMachine(m);
        assertEquals(sm1.checkPowerLimit(), StatusCode.OK);
    }

    @Test
    public void checkSystemThresholdHigh() {
        Machine m = new Machine(100);
        sm1.addMachine(m);
        assertEquals(sm1.checkPowerLimit(), StatusCode.ERROR);
    }

    @Test
    public void checkSystemThresholdOnTreshold() {
        Machine m = new Machine(10);
        sm1.addMachine(m);
        assertEquals(sm1.checkPowerLimit(), StatusCode.WARNING);
    }

    @Test
    public void addMachine() {
        int initialMachineCnt = sm2.getMachines().size();
        Machine m1 = new Machine(1);
        StatusCode result = sm2.addMachine(m1);
        assertEquals(result, StatusCode.OK);
        assertEquals(initialMachineCnt + 1, sm2.getMachines().size());
    }

}


