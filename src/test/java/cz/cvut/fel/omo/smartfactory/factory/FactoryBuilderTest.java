package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryBuilderTest {

    @Test
    void builderSetsFieldsCorrectly() {

        RepairmenPool pool = new RepairmenPool();

        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);
        pool.createRepairman("Repairman", 1.0f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();

        productionUnitManager.createWorker("Worker", JobUtils.stepDuration(1));
        productionUnitManager.createWorker("Worker", JobUtils.stepDuration(1));

        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);
        productionUnitManager.createRobot("Robot", 100);

        productionUnitManager.createMachine("Machine", 100);
        productionUnitManager.createMachine("Machine", 100);
        productionUnitManager.createMachine("Machine", 100);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.simulate();

        assertEquals("Test factory", factory.getName());
        assertEquals(4, factory.getRepairmanPool().getRepairmanList().size());
        assertEquals(2, factory.getProductionUnitManager().getAvailableUnits().get("W").size());
        assertEquals(4, factory.getProductionUnitManager().getAvailableUnits().get("R").size());
        assertEquals(3, factory.getProductionUnitManager().getAvailableUnits().get("M").size());
        assertEquals(pool, factory.getRepairmanPool());
        // pool set to listen to OutageEvents
        assertTrue(EventBusManager.getEventBus(Factory.EVENTBUS_NAME).getEventTypeListenersMap().get(EventType.OUTAGE).contains(pool));
    }
}
