package main;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TomiTests {

	private SystemManager systemManager;

	@Before
	public void setUp() {
		List<Machine> machines = new ArrayList<>();

		systemManager = new SystemManager(machines);
	}

	private Machine newMachine(double temperatureTreshold, double powerTreshold) {
		ArrayList<Sensor> sensors = new ArrayList<>();
		RandomSensor sensor = new RandomSensor();
		sensors.add(new RandomSensor());
		Machine machine = new Machine(temperatureTreshold, powerTreshold, sensors);
		double[] datas = new double[] {2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 1.0};

		//initMachineDatas(machine, datas);

		return machine;
	}

	private void addMachine(Machine machine) {
		systemManager.getMachines().add(machine);
	}

	private void addSomeMachineWithTemp(int n, int temp, int treshold) {
		MachineData lowMachineData = new MachineData();
		lowMachineData.setTemperature(temp);

		for(int i = 0; i < n; i++) {
			List<Sensor> sensors = new ArrayList<>();
			RandomSensor sensor = new RandomSensor();
			sensor.setMachineData(lowMachineData);
			sensors.add(sensor);
			addMachine(new Machine(10, 10, sensors));
		}
	}

	@Test
	public void checkIfNoHighTempMachines() {
		MachineData md = new MachineData();
		md.setTemperature(5);
		RandomSensor sensor = new RandomSensor();
		sensor.setMachineData(md);
		List<Sensor> sensors = new ArrayList<>();

		sensors.add(sensor);

		addMachine(new Machine(10, 10, sensors));
		StatusCode result = systemManager.checkIfTooHighTemp();

		assertEquals(StatusCode.OK, result);
	}

	@Test
	public void checkIfNotEnoughHighTempMachines() {
		addSomeMachineWithTemp(7, 10, 7);
		addSomeMachineWithTemp(1, 10, 15);

		StatusCode result = systemManager.checkIfTooHighTemp();

		assertEquals(StatusCode.OK, result);
	}

	@Test
	public void checkIfEnoughHighTempMachines() {
		addSomeMachineWithTemp(20, 9, 10);
		addSomeMachineWithTemp(11, 13, 10);

		StatusCode result = systemManager.checkIfTooHighTemp();

		assertEquals(StatusCode.ERROR, result);
	}
}
