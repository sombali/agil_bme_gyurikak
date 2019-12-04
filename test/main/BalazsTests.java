package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BalazsTests {

    private SystemManager systemManager;

    @Before
    public void setUp() {

        List<Machine> machines = new ArrayList<>();
        machines.add(newMachine(10,11));
        machines.add(newMachine(15,20));

        systemManager = new SystemManager(machines);
    }

    private Machine newMachine(double temperatureTreshold, double powerTreshold) {
        Machine machine = new Machine(temperatureTreshold, powerTreshold);
        double[] datas = new double[] {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 1.0};

        initMachineDatas(machine, datas);

        return machine;
    }

    private void initMachineDatas(Machine machine, double[] datas) {
        List<MachineData> last10MachineData = new ArrayList<>();
        for (double data : datas) {
            last10MachineData.add(new MachineData(1, data));
        }
        machine.setPrevious10MachineData(last10MachineData);
    }

    private void addMachine(Machine machine, double[] datas) {
        initMachineDatas(machine, datas);
        systemManager.getMachines().add(machine);
    }


    @Test
    public void checkLast10MachineDataWithoutZeros() {
        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void checkLast10MachineDataWithOneZero() {
        double[] datas = new double[] {0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        addMachine(new Machine(10, 10), datas);

        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.OK, result);

    }

    @Test
    public void checkLast10MachineDataWithMultipleZeros() {
        double[] datas = new double[] {0.0, 1.0, 2.0, 3.0, 0.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        addMachine(new Machine(10, 10), datas);

        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.ERROR, result);
    }

    @Test
    public void checkLast10TendencyOK() {
        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void checkLast10TendencyWARNING() {
        double[] datas = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 6.0, 7.0, 8.0, 9.0};
        addMachine(new Machine(10, 10), datas);

        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.WARNING, result);
    }

    @Test
    public void checkLast10TendencyERROR() {
        double[] datas = new double[] {0.0, 1.0, 2.0, 3.0, 0.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        addMachine(new Machine(10, 10), datas);

        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.ERROR, result);
    }




}
