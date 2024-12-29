package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductionLinePool implements TickObserver {

    private static final Logger LOGGER = LogManager.getLogger("Production line manager");

    /**
     * Identifier factory
     */
    private final IdentifierFactory identifierFactory = new IdentifierFactory("PRODUCTION_LINE_POOL");

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

    /**
     * Create production line in pool and give unique identifier in this pool
     *
     * @return Created production line
     */
    public ProductionLine createLine(String name, List<AbstractProductionUnit> sequence) {
        ProductionLine line = new ProductionLine(identifierFactory.create(name), sequence);
        productionLineList.add(line);
        return line;
    }
}
