package main;

import jdk.net.SocketFlow;

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


    public boolean checkIfHas10Data(Machine machine) {
        if((!machine.equals(null)) && (machine.getPrevious10MachineData().size() == 10)) {
            return true;
        } else {
            return false;
        }
    }

    public double countAvg(Machine machine) {
        double avg = 0;
        double sum = 0;
        if(checkIfHas10Data(machine)) {
            List<MachineData> dataPoints = machine.getPrevious10MachineData();

            for (MachineData data : dataPoints) {
                sum += data.getTemperature();
            }

            avg = sum / dataPoints.size();

            return avg;
        } else {
            return -1;
        }
    }



}
