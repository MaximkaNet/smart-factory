package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FactoryTest {
    @Test
    void createFactoryAndSimulate() {
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

        // Simulate
        factory.simulate();
    }
}
