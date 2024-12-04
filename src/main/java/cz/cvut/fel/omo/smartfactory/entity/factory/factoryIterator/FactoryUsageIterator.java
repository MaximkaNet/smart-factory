package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FactoryUsageIterator implements Iterator<ProductionUnit> {
    private Factory factory;
    private ProductionLine productionLine;
    @Getter
    private ProductionUnit current;

    public FactoryUsageIterator(Factory factory) {
        this.factory = factory;
        this.productionLine = factory.getProductionLines().get(0);
        this.current = productionLine.getProductionUnitChain();
    }

    // TODO: hasNext and next has to be update to iterate based on priority
    @Override
    public boolean hasNext() {
        if (current.getNext() != null) {
            return true;
        }
        int nextProductionUnitIndex = factory.getProductionLines().indexOf(productionLine) + 1;
        return nextProductionUnitIndex < factory.getProductionLines().size();
    }

    @Override
    public ProductionUnit next() {
        if (current.getNext() != null) {
            current = current.getNext();
            return current;
        }
        int nextProductionUnitIndex = factory.getProductionLines().indexOf(productionLine) + 1;
        if (nextProductionUnitIndex >= factory.getProductionLines().size()) {
            throw new NoSuchElementException("Iterator cannot move to next because is at end");
        }
        this.productionLine = factory.getProductionLines().get(nextProductionUnitIndex);
        current = productionLine.getProductionUnitChain();
        return current;
    }
}