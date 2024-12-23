package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.factory.TickObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductionLinePool implements TickObserver {

    private static final Logger LOGGER = LogManager.getLogger("Production line manager");

    /**
     * Production line list
     */
    private final List<ProductionLine> productionLineList = new ArrayList<>();

    /**
     * Create production line manager
     */
    public ProductionLinePool() {
    }

    /**
     * Add production line
     */
    public void addLine(ProductionLine line) {
        productionLineList.add(line);
    }

    @Override
    public void update(long deltaTime) {
        productionLineList.forEach(productionLine -> productionLine.process(deltaTime));
    }
}
