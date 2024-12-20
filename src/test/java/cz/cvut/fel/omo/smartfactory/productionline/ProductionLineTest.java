package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.createChain;
import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.getWorkerListWithOneStepEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductionLineTest {
    @Test
    public void createChainAndDevelopOneProduct() {
        Product templates = new Product("Tesla model S", 100);

        ProductionLine productionLine = new ProductionLine(createChain(getWorkerListWithOneStepEach()));

        boolean isAccepted = productionLine.getState().accept(templates.pop(1));
        productionLine.getState().process(1);
        Product releasedProduct = productionLine.getState().pop();
        Product productAfterPop = productionLine.getState().peek();

        assertTrue(isAccepted);
        assertNotNull(releasedProduct);
        assertNull(productAfterPop);
    }
}
