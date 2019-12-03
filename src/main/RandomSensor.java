package main;

public class RandomSensor implements Sensor {

    private MachineData machineData;

    @Override
    public MachineData getMachineData() {
        return machineData;
    }
}
