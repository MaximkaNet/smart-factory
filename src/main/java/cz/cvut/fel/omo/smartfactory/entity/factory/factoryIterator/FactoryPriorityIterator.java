package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

import java.util.Iterator;

public class FactoryPriorityIterator implements Iterator<Factory> {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Factory next() {
        return null;
    }
}
