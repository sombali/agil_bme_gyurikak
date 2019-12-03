package main;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;

public class MateTests {
    private class Machine1Sensor implements Sensor {
        @Override
        public MachineData getMachineData() {
            MachineData md = new MachineData();
            md.setTemperature(7);
            return md;
        }
    }

    private class Machine2Sensor implements Sensor {
        @Override
        public MachineData getMachineData() {
            MachineData md = new MachineData();
            md.setTemperature(8);
            return md;
        }
    }

    private SystemManager sm;

    @Before
    public void setUp() {
        List<Sensor> machine1Sensors = new ArrayList<>();
        machine1Sensors.add(new Machine1Sensor());
        Machine machine1 = new Machine(10, 0, machine1Sensors);

        List<Sensor> machine2Sensors = new ArrayList<>();
        machine2Sensors.add(new Machine2Sensor());
        Machine machine2 = new Machine(10, 0, machine2Sensors);

        List<Machine> machines = new ArrayList<>();
        machines.add(machine1);
        machines.add(machine2);
        sm = new SystemManager(machines, null, null);
    }

    @Test
    public void checkWholeSystem_highThreshold() {
        StatusCode result = sm.checkWholeSystem(20);
        // OK if threshold > 7+8
        assertSame(result, StatusCode.OK);
    }

    @Test
    public void checkWholeSystem_lowThreshold() {
        // ERROR if threshold < 7+8
        StatusCode result = sm.checkWholeSystem(10);
        assertSame(result, StatusCode.ERROR);
    }

    @Test
    public void checkWholeSystem_equalThreshold() {
        // WARN if threshold == 7+8
        StatusCode result = sm.checkWholeSystem(7 + 8);
        assertSame(result, StatusCode.WARNING);
    }


}


