package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
import cz.cvut.fel.omo.smartfactory.entity.person.Inspector;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FactoryVisitorTest {
    Factory factory;
    Director director;
    Inspector inspector;

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
                .build();

        List<Worker> workers = factory.getPeople().stream()
                .filter(person -> person instanceof Worker)
                .map(person -> (Worker) person)
                .collect(Collectors.toList());
        
        director = factory.getFirstAvailableDirector();
        inspector = factory.getFirstAvailableInspector();
        Worker worker1 = workers.get(0);
        Worker worker2 = workers.get(1);
        Worker worker3 = workers.get(2);

        ProductionLine productionLine = new ProductionLine("P1", 1);
        ProductionLine productionLine2 = new ProductionLine("P2", 2);
        productionLine.setProductionUnitChain(worker1);
        worker1.setNext(worker2);
        productionLine2.setProductionUnitChain(worker3);

        factory.setProductionLines(Arrays.asList(productionLine, productionLine2));
    }

    @Test
    public void DirectorVisitsInTreeOrder() {
        director.startVisit(factory.getFactoryTreeIterator());
        factory.simulate(5);
    }

    @Test
    public void InspectorVisitsInTreeOrder() {
        inspector.startVisit(factory.getFactoryUsageIterator());
        factory.simulate(5);
    }
}
