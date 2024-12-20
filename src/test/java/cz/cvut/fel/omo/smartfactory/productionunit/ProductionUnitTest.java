package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductionUnitTest {

    /**
     * Create chain
     */
    private AbstractProductionUnit createChain(List<AbstractProductionUnit> unitList) {
        AbstractProductionUnit first = null;
        AbstractProductionUnit current = null;

        for (AbstractProductionUnit unit : unitList) {
            if (first == null) {
                first = unit;
                current = unit;
                continue;
            }
            current.setNext(unit);
            current = unit;
        }
        return first;
    }

    private List<AbstractProductionUnit> getWorkerList() {
        return List.of(
                new Worker("Worker 1", JobUtils.stepDuration(1)),
                new Worker("Worker 2", JobUtils.stepDuration(1)),
                new Worker("Worker 3", JobUtils.stepDuration(1)),
                new Worker("Worker 4", JobUtils.stepDuration(1))
        );
    }

    @Test
    void createAndPushProductThroughChain() {
        AbstractProductionUnit chain = createChain(getWorkerList());

        Product template = new Product("The product");

        Product productFromChain = chain.processChain(template, 1);

        Product worker1Product = chain.getState().peek();

        assertNotNull(productFromChain);
        assertNull(worker1Product);
    }

    @Test
    void resetChain() {

        List<AbstractProductionUnit> workers = getWorkerList();

        AbstractProductionUnit chain = createChain(workers);

        chain.reset();

        for (AbstractProductionUnit unit : workers) {
            assertNull(unit.getNext());
        }
    }
}
