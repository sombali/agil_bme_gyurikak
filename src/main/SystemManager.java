package main;

import java.util.List;

public class SystemManager {

    private List<Machine> machines;
    private DatabaseConnector databaseConnector;
    private List<MachineData> previous10MachineData;

    public SystemManager(List<Machine> machines, DatabaseConnector databaseConnector,
        List<MachineData> previous10MachineData) {
        this.machines = machines;
        this.databaseConnector = databaseConnector;
        this.previous10MachineData = previous10MachineData;
    }

    public void queryMachines() {

    }

    public void filterMachineData() {

    }

    /*
     * összes gépnek van külön áramfelvétele, abból számítunk egy egész
     * rendszerre való reprezentációt, és arra is van egy treshold.
     * ha van pl 3 gép, egyiknek áramfelvétele 7, másiknak 8, harmadiknak 5,
     * de az egész rendszeré csak 10, akkor is hiba legyen.
     */
    public StatusCode checkWholeSystem(double wholeSystemThreshold) {
        double machineTempSum = 0;

        for(Machine machine: machines) {
            machineTempSum += machine.getMachineData().getTemperature();
        }
        if(machineTempSum < wholeSystemThreshold) {
            return StatusCode.OK;
        } else if(machineTempSum == wholeSystemThreshold) {
            return StatusCode.WARNING;
        } else {
            return StatusCode.ERROR;
        }
    }

}
