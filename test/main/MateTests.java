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
        Machine m = new Machine(1);
        sm.addMachine(m);
        assertEquals(sm.checkPowerLimit(), StatusCode.OK);
    }

}


