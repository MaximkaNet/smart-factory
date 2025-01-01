package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.createChain;
import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.getWorkerListWithOneStepEach;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductionLineTest {
    @Test
    public void createChainAndDevelopOneProduct() {
        Product template = new Product("Tesla model S");

        ProductionLine productionLine = new ProductionLine(
                Identifier.of(1, "Production line", "PL"),
                createChain(getWorkerListWithOneStepEach())
        );

        productionLine.addTemplate(template);
        productionLine.process(1);
        Product releasedProduct = productionLine.pop();

        assertNotNull(releasedProduct);
        assertEquals(template, releasedProduct);
        assertEquals(0, productionLine.getInProgress());
    }

    @ParameterizedTest
    @CsvSource({
            "'W'",
            "'M'",
            "'R'",
            "'W,M,M,R'",
            "'W,M,R,M'",
            "'M,W,M,R'",
            "'R,M,M,W'",
    })
    void chainIsCreatedInRightWay(String sequence) {
        List<String> sequenceList = Arrays.asList(sequence.split(","));

        RepairmenPool pool = new RepairmenPool();
        pool.createRepairman("Repairman 1", 1f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();
        productionUnitManager.createWorker("Worker 1", JobUtils.stepDuration(1));
        productionUnitManager.createRobot("Robot 1", 2000);
        productionUnitManager.createRobot("Robot 2", 2000);
        productionUnitManager.createMachine("Machine 1", 2000);
        productionUnitManager.createMachine("Machine 2", 2000);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.addOrder("Test order", 100, sequenceList);

        AbstractProductionUnit abstractProductionUnit = factory.getProductionLinePool().getProductionLineList().getFirst().getChain();

        // test that the chain is configured in right order
        int counter = 0;
        while (abstractProductionUnit != null) {
            assertEquals(sequenceList.get(counter), abstractProductionUnit.getId().getShortName());
            abstractProductionUnit = abstractProductionUnit.getNext();
            counter++;
        }
        assertEquals(sequenceList.size(), counter);
    }

    @ParameterizedTest
    @CsvSource({
            "'W,M,M,R'",
            "'W,W,M,R'",
            "'W,M,R,R'",
            "'W,M,R,W'",
            "'W,M,R,M'",
            "'R,M,W,R'",
    })
    void factoryThrowsRuntimeExceptionWhenUnableToConstructSequence(String sequence) {
        List<String> sequenceList = Arrays.asList(sequence.split(","));

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

        assertThrows(RuntimeException.class, () -> factory.addOrder("Test order", 100, sequenceList));
    }

    @Test
    public void unitsAreReconfiguredAfterSuccessfulCompletionOfOrder() {
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

        //setting the timer length
        TimerManager.getTimer(Factory.TIMER_NAME, 1000);

        factory.addOrder("Test order 1", 10, new ArrayList<>(Arrays.asList("M", "R", "W")));

        // after 100 ticks the order has to be done
        factory.simulate(100);

        factory.addOrder("Test order 2", 10, new ArrayList<>(Arrays.asList("R", "W", "M")));

        factory.simulate(100);
    }
}
