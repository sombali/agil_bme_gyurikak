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


    /**
     * Egy gép utolsó 10 értékéből ha az étlag magas, akkor hibás a gép
     * @param machine - a vizsgált gép
     * @return statusCode - státusz
     */
    public StatusCode checkLast10MachineDataAvg(Machine machine) {
        double sum = 0;
        double avg = 0;

        List<MachineData> dataPoints = machine.getPrevious10MachineData();

        for (MachineData point : dataPoints) {
            sum += point.getTemperature();
        }

        avg = sum / (double)dataPoints.size();
        if (avg > machine.getTemperatureTreshold()) {
            return StatusCode.ERROR;
        } else {
            return StatusCode.OK;
        }
    }


    /**
     * Ha egy gép utolsó 10 értékéből 3 magas akkor figyelmeztető jelzés, ha legalább 5 akkor hibás
     * @param machine - a vizsgált gép
     * @return statusCode - státusz
     */
    public StatusCode checkLast10MachineDataSeq(Machine machine) {
        int faultyMachines = 0;

        List<MachineData> dataPoints = machine.getPrevious10MachineData();

        for (MachineData point : dataPoints) {
            if (point.getTemperature() > machine.getTemperatureTreshold()) {
                faultyMachines++;
            }
         }
        if (faultyMachines < 3) {
            return StatusCode.OK;
        } else if (faultyMachines < 5) {
            return StatusCode.WARNING;
        } else {
            return StatusCode.ERROR;
        }
    }


    /**
     * összes gépnek van külön áramfelvétele, abból számítunk egy egész
     * rendszerre való reprezentációt, és arra is van egy treshold.
     * ha van pl 3 gép, egyiknek áramfelvétele 7, másiknak 8, harmadiknak 5,
     * de az egész rendszeré csak 10, akkor is hiba legyen.
     */
    public StatusCode checkWholeSystem() {
        double machineTempSum = 0;

        for(Machine machine: machines) {
            machineTempSum += machine.getPrevious10MachineData().get(0).getTemperature();
        }
        if(machineTempSum < this.systemPowerThreshold) {
            return StatusCode.OK;
        } else if(machineTempSum == this.systemPowerThreshold) {
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
                if (machineData.getPower() == 0.0) {
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
     * gép hozzáadása, úgy hogy meg kell nézni a rendszer gépet, és ha az
     * összesített tresholdn agyobb mint a rendszer tresholdja, akkor ne lehessen hozzáadni.
     * Az addMachine megnézi, hogy ha a gépet hozzáadva magas az egész
     * rendszer tresholdja, akkor nem lehet hozzáadni.
     */
    public StatusCode addMachine(Machine machine) {
        double powerThresholdSum = 0;
        for(Machine m: this.machines) {
            powerThresholdSum += m.getPowerTreshold();
        }
        double theoreticalTotalPower = powerThresholdSum + machine.getPowerTreshold();
        if(theoreticalTotalPower >= this.systemPowerThreshold) {
            return StatusCode.ERROR;
        } else {
            this.machines.add(machine);
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

    /**
     * Összes machine hőmérséklete, és ha 30%-nak az értéke a sajat hőmérsékleti
     * tresholdja felett van akkor gebasz van
     * @return státusz
     */
    public StatusCode checkIfTooHighTemp() {
        StatusCode statusCode;

        int highTempMachines = 0;
        for(int i = 0; i < getMachines().size(); i++) {
            if(getMachines().get(i).getMachineData().getTemperature() > getMachines().get(i).getTemperatureTreshold())
                highTempMachines++;
        }

        if((float)highTempMachines/getMachines().size() > 0.3)
            statusCode = StatusCode.ERROR;
        else statusCode = StatusCode.OK;

        return statusCode;
    }
}
