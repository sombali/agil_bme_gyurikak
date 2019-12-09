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


    public StatusCode checkIfHas10Data(Machine machine) {
        if((!machine.equals(null)) && (machine.getPrevious10MachineData().size() == 10)) {
            return StatusCode.OK;
        } else {
            return StatusCode.NODATA;
        }
    }



}
