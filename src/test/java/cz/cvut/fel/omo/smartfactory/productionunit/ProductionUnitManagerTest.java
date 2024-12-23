package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductionUnitManagerTest {

    @Test
    void getWorkerChain() {
        IdentifierFactory idFactory = new IdentifierFactory("PROD_UNIT");

        ProductionUnitManager manager = new ProductionUnitManager();

        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));

        List<AbstractProductionUnit> chain = manager.getUnits(List.of("W", "W", "W"));

        long availableUnitsSize = manager.getAvailableUnits().get("W").size();

        assertEquals(1, availableUnitsSize);
        assertEquals(3, chain.size());
    }

    @Test
    void getAvailableUnitsAndPushItToBusy() {
        IdentifierFactory idFactory = new IdentifierFactory("PROD_UNIT");

        ProductionUnitManager manager = new ProductionUnitManager();

        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));

        List<AbstractProductionUnit> chain = manager.getUnits(List.of("W", "M", "M", "R", "W", "R", "M"));

        chain.forEach(manager::addBusyUnit);

        assertEquals(3, manager.getBusyUnits().size());
        assertEquals(3, manager.getBusyUnits().get("M").size());
        assertEquals(2, manager.getBusyUnits().get("R").size());
        assertEquals(2, manager.getBusyUnits().get("W").size());

        assertEquals(3, manager.getAvailableUnits().size());
        assertEquals(0, manager.getAvailableUnits().get("W").size());
        assertEquals(2, manager.getAvailableUnits().get("R").size());
        assertEquals(1, manager.getAvailableUnits().get("M").size());
    }

    @Test
    void getAvailableUnitsButManagerHasNoAvailableUnits() {
        IdentifierFactory idFactory = new IdentifierFactory("PROD_UNIT");

        ProductionUnitManager manager = new ProductionUnitManager();

        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Worker(idFactory.create("Worker", "W"), JobUtils.stepDuration(1)));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Robot(idFactory.create("Robot", "R"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));
        manager.addAvailableUnit(new Machine(idFactory.create("Machine", "M"), 200, null));

        Throwable thrown = assertThrows(
                RuntimeException.class,
                () -> manager.getUnits(List.of("W", "M", "M", "R", "W", "R", "M", "W", "W")),
                "NOT MY EXCEPTION"
        );

        assertEquals("Available unit was not found", thrown.getMessage());

        assertEquals(3, manager.getAvailableUnits().size());
        assertEquals(0, manager.getAvailableUnits().get("W").size());
        assertEquals(2, manager.getAvailableUnits().get("R").size());
        assertEquals(1, manager.getAvailableUnits().get("M").size());
    }
}
