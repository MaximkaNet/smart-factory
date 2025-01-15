package cz.cvut.fel.omo;

import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;

import java.util.List;

public class Main {
    private static final SimulationType SIMULATION_TYPE = SimulationType.DEFAULT;
    private static final long TICK_LENGTH_MS = 10;

    public static void main(String[] args) throws InterruptedException {
        // Set timer
        TimerManager.getTimer(Factory.TIMER_NAME, TICK_LENGTH_MS);

        // Create factory
        Factory factory = new Factory("Smart factory");

        ProductionUnitManager unitManager = factory.getProductionUnitManager();

        unitManager.createWorker("Worker", JobUtils.stepDuration(1));
        unitManager.createWorker("Worker", JobUtils.stepDuration(1));
        unitManager.createRobot("Robot", 100);
        unitManager.createMachine("Machine", 100);

        // Create repairmen
        RepairmenPool pool = factory.getRepairmanPool();
        pool.createRepairman("Repairman", 10.0f);
        pool.createRepairman("Repairman", 10.0f);
        pool.createRepairman("Repairman", 10.0f);
        pool.createRepairman("Repairman", 10.0f);

        factory.addOrder("Tesla model S", 10, List.of("W", "W", "R", "M"));

        int cycleNumber = 40;

        // Simulate
        if (SIMULATION_TYPE == SimulationType.DEFAULT) {
            factory.simulate(cycleNumber);
        } else if (SIMULATION_TYPE == SimulationType.REALTIME) {
            factory.simulateRealtime(cycleNumber);
        }
    }
}
