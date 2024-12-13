package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryUsageIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
import cz.cvut.fel.omo.smartfactory.entity.person.Inspector;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class FactoryIteratorTest {
    Factory factory;
    Director director;
    Inspector inspector;
    Worker worker1;
    Worker worker2;
    Worker worker3;
    Machine machine1;
    Machine machine2;
    Machine machine3;

    @BeforeEach
    public void setUp() {
        factory = Factory.builder()
                .setName("Factory Name")
                .setTickLength(250)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .build()
                )
                .addWorker("W", "Worker", "First", 5)
                .addWorker("W", "Worker", "Second", 4)
                .addWorker("W", "Worker", "Third", 6)
                .addDirector("D", "Sefko", "Prvy")
                .addInspector("I", "Inspektor", "Prvy")
                .addMachine("M", "Machine 1000", 30, 10)
                .addMachine("M", "Machine 2000", 25, 10)
                .addMachine("M", "Machine 3000", 15, 5)
                .build();

        List<Worker> workers = factory.getPeople().stream()
                .filter(person -> person instanceof Worker)
                .map(person -> (Worker) person)
                .collect(Collectors.toList());
        List<Machine> machines = factory.getMachines();

        director = factory.getFirstAvailableDirector();
        inspector = factory.getFirstAvailableInspector();

        worker1 = workers.get(0);
        worker2 = workers.get(1);
        worker3 = workers.get(2);

        machine1 = machines.get(0);
        machine1.setActualHealth(3); // 70% usage: 7hp usage

        machine2 = machines.get(1);
        machine2.setActualHealth(7); // 30% usage: 3hp usage

        machine3 = machines.get(2);
        machine3.setActualHealth(3); // 40% usage: 2hp usage

        ProductionLine productionLine = new ProductionLine("P1", 1);
        ProductionLine productionLine2 = new ProductionLine("P2", 2);

        productionLine.setProductionUnitChain(worker1);
        worker1.setNext(worker2);
        worker2.setNext(machine1);
        machine1.setNext(machine2);

        productionLine2.setProductionUnitChain(worker3);
        worker3.setNext(machine3);

        factory.setProductionLines(Arrays.asList(productionLine, productionLine2));
    }

    @Test
    public void TreeIteratorIteratesThroughFactoryInTreeStructure() {
        FactoryTreeIterator iterator = factory.getFactoryTreeIterator();
        assertEquals(worker1, iterator.getCurrent());
        assertEquals(worker2, iterator.next());
        assertEquals(machine1, iterator.next());
        assertEquals(machine2, iterator.next());
        assertEquals(worker3, iterator.next());
        assertEquals(machine3, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void TreeIteratorDoesNotThrowExceptionOnEmptyFactory() {
        Factory emptyFactory = Factory.builder()
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .build()
                )
                .build();
        FactoryTreeIterator iterator = emptyFactory.getFactoryTreeIterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void UsageIteratorIteratesThroughAbstractFactoryEquipmentBasedOnUsageDescending() {
        FactoryUsageIterator iterator = factory.getFactoryUsageIterator();
        assertEquals(machine1, iterator.getCurrent());
        assertEquals(machine3, iterator.next());
        assertEquals(machine2, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void UsageIteratorDoesNotThrowExceptionOnEmptyFactory() {
        Factory emptyFactory = Factory.builder()
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .build()
                )
                .build();
        emptyFactory.setProductionLines(new ArrayList<>());
        FactoryUsageIterator iterator = emptyFactory.getFactoryUsageIterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
