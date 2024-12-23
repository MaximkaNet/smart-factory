package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.factory.TickObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductionLineManager implements TickObserver {

    private static final Logger LOGGER = LogManager.getLogger("Production line manager");

    /**
     * Production line list
     */
    private final List<ProductionLine> productionLineList = new ArrayList<>();

    /**
     * Create production line manager
     */
    public ProductionLineManager() {
    }

    /**
     * Add production line
     */
    public void addLine(ProductionLine line) {
        productionLineList.add(line);
    }

    @Override
    public void update(long deltaTime) {
//        for (Storage storage : storages) {
//            if (storage.isEmpty()) {
//                StorageConnector connector = storage.getConnector();
//                ProductionLine productionLine = connector.getProductionLine();
//                if (productionLine.productIsDone()) {
//                    removingQueue.add(productionLine);
//                }
//            }
//        }

//        productionLineList.forEach(productionLine -> {
//            productionLine.getState().process(deltaTime);
//            if (productionLine.isFinished()) {
//                factory.addCompletedSeries(productionLine.pop());
//                productionLineList.remove(productionLine);
//            }
//        });
    }
}
