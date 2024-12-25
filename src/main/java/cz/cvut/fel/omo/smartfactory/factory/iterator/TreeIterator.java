package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Tree iterator
 */
public class TreeIterator implements FactoryIterator {

    /**
     * Production line iterator
     */
    private final Iterator<ProductionLine> productionLineIterator;

    /**
     * Current production unit
     */
    private AbstractProductionUnit currentUnit = null;

    /**
     * Create tree iterator
     */
    public TreeIterator(List<ProductionLine> productionLines) {
        productionLineIterator = productionLines.iterator();
    }

    @Override
    public boolean hasNext() {
        return currentUnit != null && currentUnit.getNext() != null || productionLineIterator.hasNext();
    }

    @Override
    public AbstractProductionUnit next() {
        if (currentUnit != null && currentUnit.getNext() != null) {
            currentUnit = currentUnit.getNext();
            return currentUnit;
        }

        // Get new chain if exists
        if (!productionLineIterator.hasNext()) {
            throw new NoSuchElementException("No production lines available");
        }

        // Get chain of responsibility and set first unit as current unit
        ProductionLine currentLine = productionLineIterator.next();
        currentUnit = currentLine.getChain();

        // Check new chain
        if (currentUnit == null) {
            throw new NoSuchElementException("The production line has no chain (chain is null)");
        }

        return currentUnit;
    }
}
