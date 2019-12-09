package main;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TomiTests {
	private SystemManager systemManager;

	@Before
	public void setUp() {
		List<Machine> machines = new ArrayList<>();

		systemManager = new SystemManager(machines);
	}

	private void addMachine(Machine machine) {
		systemManager.getMachines().add(machine);
	}

	private void addSomeMachineWithTemp(int n, int temp, int treshold) {
		MachineData md = new MachineData();
		md.setTemperature(temp);


		for(int i = 0; i < n; i++) {
			ArrayList<Sensor> sensors = new ArrayList<>();
			RandomSensor sensor = new RandomSensor();
			sensor.setMachineData(md);
			sensors.add(sensor);
			addMachine(new Machine(treshold, 1, sensors));
		}

	}

	@Test
	public void testEmptyMachines() {
		StatusCode result = systemManager.checkIfTooHighTemp();

		assertEquals(StatusCode.OK, result);
	}

	@Test
	public void testOneBadElement() {
		addSomeMachineWithTemp(1, 7, 10);
		addSomeMachineWithTemp(1, 13, 10);

		StatusCode result = systemManager.checkIfTooHighTemp();
		assertEquals(StatusCode.ERROR, result);
	}

	@Test
	public void test3TimesGoodAsHigh() {
		addSomeMachineWithTemp(3, 7, 10);
		addSomeMachineWithTemp(1, 13, 10);

		StatusCode result = systemManager.checkIfTooHighTemp();
		assertEquals(StatusCode.OK, result);
	}
}
