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

    public StatusCode checkIfHasZero() {
        for (Machine machine : machines) {
            for (MachineData data : machine.getPrevious10MachineData()) {
                if(data.getPower() == 0) {
                    return StatusCode.WARNING;
                }
            }
        }
        return StatusCode.OK;
    }



}
