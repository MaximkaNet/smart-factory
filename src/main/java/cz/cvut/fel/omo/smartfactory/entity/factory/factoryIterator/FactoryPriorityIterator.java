package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

import java.util.Iterator;

public class FactoryPriorityIterator implements Iterator<ProductionUnit> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public ProductionUnit next() {
        return null;
    }
}
