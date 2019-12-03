package main;

public interface DatabaseConnector {

    void save(MachineData machineData);
    MachineData load(MachineData machineData);

}
