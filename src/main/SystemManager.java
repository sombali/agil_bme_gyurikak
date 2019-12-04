package main;

import java.util.Collections;
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
        int counter = 0;

        for (Machine machine : machines) {
            for (MachineData data : machine.getPrevious10MachineData()) {
                if(data.getPower() == 0) {
                    counter++;
                }
            }
        }

        if(counter > 1) {
            return StatusCode.ERROR;
        } else if (counter == 1) {
            return StatusCode.WARNING;
        } else {
            return StatusCode.OK;
        }
    }

    public StatusCode checkPreviousTenTendency() {
        for (Machine machine : machines) {

            if(machine.getPrevious10MachineData().isEmpty()) {
                return StatusCode.ERROR;
            }

            List<MachineData> previous10MachineData = machine.getPrevious10MachineData();
            Collections.reverse(previous10MachineData);

            int numberOfHigher = 0;
            boolean hasLower = false;

            for(int i = 0; i < 4; i++) {
                if (!hasLower && previous10MachineData.get(i).getPower() > previous10MachineData.get(i+1).getPower()) {
                    numberOfHigher++;
                } else {
                    hasLower = true;
                }
            }

            if(numberOfHigher == 3) {
                return StatusCode.WARNING;
            }
        }
        return StatusCode.OK;
    }


}
