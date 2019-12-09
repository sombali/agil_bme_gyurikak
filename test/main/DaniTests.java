package main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DaniTests {

    private SystemManager systemManager;

    @Before
    public void setUp() {

        ArrayList<Machine> machines = new ArrayList<>();
        Machine machine = newMachine(10, 10);
        machines.add(machine);

        systemManager = new SystemManager(machines);
    }

    private Machine newMachine(double temperatureTreshold, double powerTreshold) {
        Machine machine = new Machine(temperatureTreshold, powerTreshold);
        double[] datas = new double[]{2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 1.0};

        initMachineDatas(machine, datas);

        return machine;
    }

    private void initMachineDatas(Machine machine, double[] datas) {
        List<MachineData> last10MachineData = new ArrayList<>();
        for (double data : datas) {
            last10MachineData.add(new MachineData(data, 1));
        }
        machine.setPrevious10MachineData(last10MachineData);
    }

    private void addMachine(Machine machine, double[] datas) {
        initMachineDatas(machine, datas);
        systemManager.getMachines().add(machine);
    }


    @Test
    public void checkIfHas10Data() {
        Machine machine = systemManager.getMachines().get(0);
        boolean status = systemManager.checkIfHas10Data(machine);

        assertEquals(true, status);
    }

    @Test
    public void countAvg() {
        double[] datas = new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
        addMachine(newMachine(10, 10), datas);
        Machine machine = systemManager.getMachines().get(systemManager.getMachines().size() - 1);

        double avg = systemManager.countAvg(machine);
        double epsilon = 0.00001;
        assertEquals(10, avg, epsilon);
    }

    @Test
    public void checkLast10MachineDataAvgEquals() {
        double[] datas = new double[] {10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 10.0};
        addMachine(newMachine(10, 10), datas);
        Machine machine = systemManager.getMachines().get(systemManager.getMachines().size() - 1);
        StatusCode status = systemManager.checkLast10MachineDataAvg(machine);

        assertEquals(StatusCode.OK, status);
    }
}