package main;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {

    private List<Machine> machines;
    private DatabaseConnector databaseConnector;
    private List<MachineData> previous10MachineData;

    public SystemManager(List<Machine> machines) {
        this.machines = machines;
    }

    public SystemManager(List<Machine> machines, DatabaseConnector databaseConnector,
                         List<MachineData> previous10MachineData) {
        this.machines = machines;
        this.databaseConnector = databaseConnector;
        this.previous10MachineData = previous10MachineData;
    }

    public List<Machine> getMachines() {
        return machines;
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

    /**
     * Ha szerepel valamelyik gép utolsó 10 eredménye között többször is 0 power,
     * akkor hibát jelez, egyébként nem.
     * @return státusz
     */
    public StatusCode checkLast10MachineDataIfZero() {

        List<Machine> faultyMachines = new ArrayList<>();

        for (Machine machine : machines) {
            List<MachineData> last10MachineData = machine.getPrevious10MachineData();

            int faulty = 0;

            for (MachineData machineData : last10MachineData) {
                if (machineData.getPower() == 0) {
                    faulty += 1;
                }
            }

            if(faulty > 1) {
                faultyMachines.add(machine);
            }
        }

        if(faultyMachines.size() != 0) {
            return StatusCode.ERROR;
        } else {
            return StatusCode.OK;
        }

    }


    /**
     * Ha az a gép utolsó power-je növekszik már 4 vagy 5 lekérdezés óta akkor warningot dob,
     * ha több mint 5 lekérdezés óta növekszik akkor errort, ha pedig 4 alatt akkor OK-ot.
     * @return státuszkód
     */
    public StatusCode checkIncresingPowerTendendcy() {

        StatusCode statusCode;

        for (Machine machine : machines) {
            List<MachineData> last10MachineData = machine.getPrevious10MachineData();

            int size = last10MachineData.size()-1;

            double tmpPower = last10MachineData.get(size).getPower();

            int numberOfIncreasingPower = 0;

            for (int i = last10MachineData.size() - 2; i >= 0; i--) {
                if(tmpPower > last10MachineData.get(i).getPower()) {
                    numberOfIncreasingPower++;
                }
                tmpPower = last10MachineData.get(i).getPower();
            }

            if(numberOfIncreasingPower > 5) {
                return StatusCode.ERROR;
            } else if(numberOfIncreasingPower > 3) {
                return StatusCode.WARNING;
            }

        }

        return StatusCode.OK;
    }


}
