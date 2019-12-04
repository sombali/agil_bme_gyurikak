package main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;

public class MateTests {
    private List<Machine> createMachineListFromDataPoints(double[] sensorData) {
        List<Machine> machines = new ArrayList<>();
        for (double data : sensorData) {
            Machine machine = new Machine(10, 10, null);
            List<MachineData> previous10MachineData = new ArrayList<>();
            previous10MachineData.add(new MachineData(data, 0));
            machine.setPrevious10MachineData(previous10MachineData);
            machines.add(machine);
        }
        return machines;
    }

    private List<Machine> createMachineListFromThresholdPoints(double[] thresholdData) {
        List<Machine> machines = new ArrayList<>();
        for (double data : thresholdData) {
            Machine machine = new Machine(0, data, null);
            machines.add(machine);
        }
        return machines;
    }

    @Test
    public void checkWholeSystem_highThreshold() {
        double[] currentSensorData = new double[] {2.0, 4.0};
        SystemManager sm = new SystemManager(createMachineListFromDataPoints(currentSensorData));
        StatusCode result = sm.checkWholeSystem();
        // OK if current sensor data < 10 (system threshold)
        assertEquals(result, StatusCode.OK);
    }

    @Test
    public void checkWholeSystem_lowThreshold() {
        double[] currentSensorData = new double[] {9.0, 4.0};
        SystemManager sm = new SystemManager(createMachineListFromDataPoints(currentSensorData));
        StatusCode result = sm.checkWholeSystem();
        // ERROR if current sensor data > 10 (system threshold)
        assertEquals(result, StatusCode.ERROR);
    }

    @Test
    public void checkWholeSystem_equalThreshold() {
        double[] currentSensorData = new double[] {5.0, 5.0};
        SystemManager sm = new SystemManager(createMachineListFromDataPoints(currentSensorData));
        StatusCode result = sm.checkWholeSystem();
        // WARN if current sensor data == 10 (system threshold)
        assertEquals(result, StatusCode.WARNING);
    }

    @Test
    public void addMachine_belowThreshold() {
        double[] currentMachineThresholds = new double[] {1, 2};
        SystemManager sm = new SystemManager(createMachineListFromThresholdPoints(currentMachineThresholds));
        Machine m = new Machine(0, 6.0, null);
        StatusCode result = sm.addMachine(m);
        assertEquals(result, StatusCode.OK);
    }

    @Test
    public void addMachine_overThreshold() {
        double[] currentMachineThresholds = new double[] {1, 2, 4, 6};
        SystemManager sm = new SystemManager(createMachineListFromThresholdPoints(currentMachineThresholds));
        Machine m = new Machine(0, 1.0, null);
        StatusCode result = sm.addMachine(m);
        assertEquals(result, StatusCode.ERROR);
    }

    @Test
    public void addMachine_onThreshold() {
        double[] currentMachineThresholds = new double[] {7};
        SystemManager sm = new SystemManager(createMachineListFromThresholdPoints(currentMachineThresholds));
        Machine m = new Machine(0, 3.0, null);
        StatusCode result = sm.addMachine(m);
        assertEquals(result, StatusCode.ERROR);
    }
}


