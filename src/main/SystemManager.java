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

    /**
     * Összes machine hőmérséklete, és ha 30%-nak az értéke a sajat hőmérsékleti
     * tresholdja felett van akkor gebasz van
     * @return státusz
     */
    public StatusCode checkIfTooHighTemp() {
        StatusCode statusCode = StatusCode.OK;

        int numberOfHighs = 0;

        for(Machine i:machines) {
            if(i.getMachineData().getTemperature() > i.getTemperatureTreshold())
                numberOfHighs++;
        }

        if((float)numberOfHighs/getMachines().size() > 0.3)
            statusCode = StatusCode.ERROR;

        return statusCode;
    }
}
