package cz.cvut.fel.omo.smartfactory.entity.productionline;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ProductionLineManager implements FactoryEventListener, TickObserver {

    private static final Logger LOGGER = LogManager.getLogger("Production line manager");

    /**
     * Production line list
     */
    private final List<ProductionLine> productionLineList = new ArrayList<>();

    /**
     * List of available units
     */
    private final List<ProductionUnit> availableUnits;

    /**
     * List of unit that on production lines
     */
    private final List<ProductionUnit> onLineUnits;

    /**
     * Create production line manager
     *
     * @param availableUnits List of production units
     */
    public ProductionLineManager(List<ProductionUnit> availableUnits) {
        this.availableUnits = availableUnits;
        this.onLineUnits = new ArrayList<>();
    }

    /**
     * Get production line factory
     */
    public ProductionLineFactory getLineFactory() {
        return new ProductionLineFactory(availableUnits);
    }

    /**
     * Add production line
     */
    public void addLine(ProductionLine line) {
        productionLineList.add(line);

        updateProductionUnitLists();
    }

    /**
     * Move production units between availableUnits and onLineUnits
     */
    private void updateProductionUnitLists() {
        List<ProductionUnit> tempAvailableList = new ArrayList<>();
        List<ProductionUnit> tempNotAvailableList = new ArrayList<>();

        // Get units that are on the line
        for (ProductionUnit unit : availableUnits) {
            if (!unit.isAvailable() && availableUnits.remove(unit)) {
                tempNotAvailableList.add(unit);
            }
        }

        // Get unit that are available
        for (ProductionUnit unit : onLineUnits) {
            if (unit.isAvailable() && onLineUnits.remove(unit)) {
                tempAvailableList.add(unit);
            }
        }

        availableUnits.addAll(tempAvailableList);
        onLineUnits.addAll(tempNotAvailableList);
    }


    @Override
    public void receiveEvent(FactoryEvent event) {
//        if (!(event instanceof SeriesFinishedEvent)) {
//            return;
//        }
//        SeriesFinishedEvent seriesFinishedevent = (SeriesFinishedEvent) event;
//        ProductionLine productionLine = seriesFinishedevent.getProductionLine();
//
//        productionLineList.remove(productionLine);
    }

    @Override
    public void update(long deltaTime) {
//        productionLineList.forEach(productionLine -> {
//            productionLine.getState().process(deltaTime);
//            if (productionLine.isFinished()) {
//                factory.addCompletedSeries(productionLine.pop());
//                productionLineList.remove(productionLine);
//            }
//        });
    }
}
