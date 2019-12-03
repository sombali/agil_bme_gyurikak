package main;


import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class BalazsTests {

    private SystemManager systemManager;

    @Before
    private void setUp() {

        List<Machine> machines = new ArrayList<>();
        machines.add(newMachine(10,11));
        machines.add(newMachine(15,20));

        systemManager = new SystemManager(machines);
    }

    private Machine newMachine(double temperatureTreshold, double powerTreshold) {
        Machine machine = new Machine(temperatureTreshold, powerTreshold);
        initMachineDatas(machine);
        return machine;
    }

    private void initMachineDatas(Machine machine1) {
        List<MachineData> last10MachineData = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            last10MachineData.add(new MachineData(i+1, i+5));
        }
        machine1.setPrevious10MachineData(last10MachineData);
    }
}
