package main;

import java.util.List;

public class Machine {
    private int type;
    private double temperatureTreshold;
    private double powerTreshold;
    private List<Sensor> sensors;
    private List<MachineData> previous10MachineData;

    public Machine(double temperatureTreshold, double powerTreshold) {
        this.temperatureTreshold = temperatureTreshold;
        this.powerTreshold = powerTreshold;
    }

    public Machine(double temperatureTreshold, double powerTreshold,
                   List<Sensor> sensors) {
        this.temperatureTreshold = temperatureTreshold;
        this.powerTreshold = powerTreshold;
        this.sensors = sensors;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTemperatureTreshold() {
        return temperatureTreshold;
    }

    public void setTemperatureTreshold(double temperatureTreshold) {
        this.temperatureTreshold = temperatureTreshold;
    }

    public double getPowerTreshold() {
        return powerTreshold;
    }

    public void setPowerTreshold(double powerTreshold) {
        this.powerTreshold = powerTreshold;
    }

    public MachineData getMachineData() {
        return this.sensors.get(0).getMachineData();
    }

    public List<MachineData> getPrevious10MachineData() {
        return previous10MachineData;
    }

    public void setPrevious10MachineData(List<MachineData> previous10MachineData) {
        this.previous10MachineData = previous10MachineData;
    }

    public void addMachineData(MachineData machineData) {
        previous10MachineData.add(machineData);
    }
}
