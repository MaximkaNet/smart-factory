package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.util.Iterator;

public class FactoryPriorityIterator implements Iterator<ProductionUnit> {
    private Factory factory;
    private ProductionLine productionLine;
    @Getter
    private ProductionUnit current;

    public FactoryPriorityIterator(Factory factory) {
        this.factory = factory;
        this.productionLine = factory.getProductionLines().get(0);
        this.current = productionLine.getProductionUnitChain();
    }

    @Override
    public boolean hasNext() {
        return false;
        // TODO
    }

    @Override
    public ProductionUnit next() {
        return null;
        // TODO
    }
}