package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.createChain;
import static cz.cvut.fel.omo.smartfactory.helpers.ChainUtils.getWorkerListWithOneStepEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductionLineTest {
    @Test
    public void createChainAndDevelopOneProduct() {
        Product template = new Product("Tesla model S");

        ProductionLine productionLine = new ProductionLine(createChain(getWorkerListWithOneStepEach()));

        productionLine.addTemplate(template);
        productionLine.process(1);
        Product releasedProduct = productionLine.pop();

        assertNotNull(releasedProduct);
        assertEquals(template, releasedProduct);
        assertEquals(0, productionLine.getInProgress());
    }
}
