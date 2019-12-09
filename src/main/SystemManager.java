package main;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {

    private List<Machine> machines;
    private DatabaseConnector databaseConnector;
    private List<MachineData> previous10MachineData;
    private double systemPowerThreshold;
    private double systemTemperatureThreshold;

    public SystemManager(List<Machine> machines) {
        this.machines = machines;
        this.systemPowerThreshold = 10;
        this.systemTemperatureThreshold = 100;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void queryMachines() {

    }

    public void filterMachineData() {

    }


    public StatusCode checkPowerLimit() {
        double powerSum = 0;
        for(Machine m: this.machines) {
            powerSum += m.getPowerTreshold();
        }
        if(powerSum < this.systemPowerThreshold) {
            return StatusCode.OK;
        } else {
            return null;
        }
    }
}
