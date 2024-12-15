package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FactoryTreeIterator implements Iterator<ProductionUnit> {
    private Factory factory;
    private ProductionLine productionLine;
    @Getter
    private ProductionUnit current;

    public FactoryTreeIterator(Factory factory) {
        this.factory = factory;
        if (factory.getProductionLines() == null) {
            return;
        }
        if (factory.getProductionLines().isEmpty()) {
            return;
        }
        this.productionLine = factory.getProductionLines().get(0);
        this.current = productionLine.getProductionUnitChain();
    }

    @Override
    public boolean hasNext() {
        if (current == null) {
            return false;
        }
        if (current.getNext() != null) {
            return true;
        }
        int nextProductionUnitIndex = factory.getProductionLines().indexOf(productionLine) + 1;
        return nextProductionUnitIndex < factory.getProductionLines().size();
    }

    @Override
    public ProductionUnit next() {
        if (current == null) {
            throw new NoSuchElementException("The current is not set, iterator cannot iterate");
        }
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