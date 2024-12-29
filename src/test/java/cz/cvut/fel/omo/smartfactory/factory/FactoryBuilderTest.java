package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryBuilderTest {

    @Test
    void buildComplexFactory() {

        RepairmenPool pool = new RepairmenPool();

        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();

        productionUnitManager.createWorker("Worker", JobUtils.stepDuration(1));
        productionUnitManager.createWorker("Worker", JobUtils.stepDuration(1));
        productionUnitManager.createWorker("Worker", JobUtils.stepDuration(1));

        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.simulate();

        assertEquals("Test factory", factory.getName());
        assertEquals(pool, factory.getRepairmanPool());
    }
}
