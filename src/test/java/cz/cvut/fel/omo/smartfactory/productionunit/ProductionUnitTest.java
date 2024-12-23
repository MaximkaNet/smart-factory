package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.createChain;
import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.getWorkerListWithOneStepEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductionUnitTest {
    @Test
    void createAndPushProductThroughChain() {
        AbstractProductionUnit chain = createChain(getWorkerListWithOneStepEach());

        Product template = new Product("The product");

        chain.getState().accept(template);
        Product productFromChain = chain.processChain(1);

        Product worker1Product = chain.getState().peek();

        assertNotNull(productFromChain);
        assertNull(worker1Product);
    }

    @Test
    void resetChain() {

        List<AbstractProductionUnit> workers = getWorkerListWithOneStepEach();

        AbstractProductionUnit chain = createChain(workers);

        chain.reset();

        for (AbstractProductionUnit unit : workers) {
            assertNull(unit.getNext());
        }
    }
}
