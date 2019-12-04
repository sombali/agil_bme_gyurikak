package main;

import static org.junit.Assert.*;

import jdk.net.SocketFlow;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BalazsTests {

    private SystemManager systemManager;
    private Random random;

    @Before
    public void setUp() {

        List<Machine> machines = new ArrayList<>();
        machines.add(newMachine(10,11));
        machines.add(newMachine(15,20));

        random = new Random();
        systemManager = new SystemManager(machines);
    }

    private Machine newMachine(double temperatureTreshold, double powerTreshold) {
        Machine machine = new Machine(temperatureTreshold, powerTreshold);
        initGoodMachineDatas(machine);
        return machine;
    }

    private void initGoodMachineDatas(Machine machine1) {
        List<MachineData> last10MachineData = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            last10MachineData.add(new MachineData(i+1, 2));
        }
        last10MachineData.add(new MachineData(10,1));
        machine1.setPrevious10MachineData(last10MachineData);
    }

    @Test
    public void checkLast10MachineDataWithoutZeros() {
        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void checkLast10MachineDataWithOneZero() {
        addMachineWithZero();

        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.OK, result);

    }

    private void addMachineWithZero() {
        Machine machine = new Machine(10,10);
        List<MachineData> last10MachineData = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            last10MachineData.add(new MachineData(i+5, i));
        }
        machine.setPrevious10MachineData(last10MachineData);

        systemManager.getMachines().add(machine);
    }

    @Test
    public void checkLast10MachineDataWithMultipleZeros() {
        addMachineWithMultipleZeros();

        StatusCode result = systemManager.checkLast10MachineDataIfZero();

        assertEquals(StatusCode.ERROR, result);
    }

    private void addMachineWithMultipleZeros() {
        Machine machine = new Machine(10,10);
        List<MachineData> last10MachineData = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            last10MachineData.add(new MachineData(i+5, i));
        }
        last10MachineData.add(new MachineData(10, 0));
        for(int i = 5; i < 10; i++) {
            last10MachineData.add(new MachineData(i+5, i));
        }

        machine.setPrevious10MachineData(last10MachineData);

        systemManager.getMachines().add(machine);
    }

    @Test
    public void checkLast10TendencyOK() {
        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.OK, result);
    }

    @Test
    public void checkLast10TendencyWARNING() {
        addMachineWithFourIncreasingData();

        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.WARNING, result);
    }

    private void addMachineWithFourIncreasingData() {
        Machine machine = new Machine(10,10);
        List<MachineData> last10MachineData = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            last10MachineData.add(new MachineData(i+5, 2));
        }
        for(int i = 6; i < 10; i++) {
            last10MachineData.add(new MachineData(i+5, i));
        }
        machine.setPrevious10MachineData(last10MachineData);

        systemManager.getMachines().add(machine);
    }

    @Test
    public void checkLast10TendencyERROR() {
        addMachineWithZero();

        StatusCode result = systemManager.checkIncresingPowerTendendcy();

        assertEquals(StatusCode.ERROR, result);
    }



}
