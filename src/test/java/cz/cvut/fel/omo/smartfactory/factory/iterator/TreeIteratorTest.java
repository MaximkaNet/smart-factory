package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeIteratorTest {
    @Test
    void createTwoChainsAndIterateAndGetExceptionInTheEnd() {
        Worker worker1 = new Worker(Identifier.of(1), JobUtils.stepDuration(1));
        Worker worker2 = new Worker(Identifier.of(2), JobUtils.stepDuration(1));
        Worker worker3 = new Worker(Identifier.of(3), JobUtils.stepDuration(1));
        Worker worker4 = new Worker(Identifier.of(4), JobUtils.stepDuration(1));

        worker1.setNext(worker2);
        worker2.setNext(worker3);
        worker3.setNext(worker4);

        Robot robot1 = new Robot(Identifier.of(1), 100);
        Robot robot2 = new Robot(Identifier.of(1), 100);
        Robot robot3 = new Robot(Identifier.of(1), 100);

        robot1.setNext(robot2);
        robot2.setNext(robot3);

        List<ProductionLine> productionLines = List.of(
                new ProductionLine(Identifier.of(1, "Production line", "PL"), worker1),
                new ProductionLine(Identifier.of(1, "Production line", "PL"), robot1)
        );

        TreeIterator iterator = new TreeIterator(productionLines);

        assertTrue(iterator.hasNext());
        assertEquals(worker1, iterator.next());
        assertEquals(worker2, iterator.next());
        assertEquals(worker3, iterator.next());
        assertEquals(worker4, iterator.next());
        assertEquals(robot1, iterator.next());
        assertEquals(robot2, iterator.next());
        assertEquals(robot3, iterator.next());
        assertFalse(iterator.hasNext());

        Throwable throwable = assertThrows(NoSuchElementException.class, iterator::next, "NEXT NOT FOUND");
        assertEquals("No production lines available", throwable.getMessage());
    }

    @Test
    public void createFactoryAndIterate() {
        RepairmenPool pool = new RepairmenPool();
        pool.createRepairman("Repairman 1", 1f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();
        productionUnitManager.createWorker("Worker 1", JobUtils.stepDuration(1));
        productionUnitManager.createRobot("Robot 1", 2000);
        productionUnitManager.createMachine("Machine 1", 2000);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.addOrder("Test order 1", 10, new ArrayList<>(Arrays.asList("M", "R", "W")));

        FactoryIterator iterator = factory.getTreeIterator();

        assertTrue(iterator.hasNext());
        assertInstanceOf(Machine.class, iterator.next());
        assertTrue(iterator.hasNext());
        assertInstanceOf(Robot.class, iterator.next());
        assertTrue(iterator.hasNext());
        assertInstanceOf(Worker.class, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next, "NEXT NOT FOUND");
    }
}
