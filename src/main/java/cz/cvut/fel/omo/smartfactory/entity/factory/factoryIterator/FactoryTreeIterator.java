package cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.util.Iterator;

public class FactoryTreeIterator implements Iterator<ProductionUnit> {
    private Factory factory;
    private ProductionLine productionLine;
    @Getter
    private ProductionUnit current;

    public FactoryTreeIterator(Factory factory, ProductionLine productionLine, ProductionUnit current) {
        this.factory = factory;
        this.productionLine = productionLine;
        this.current = current;
        // TODO: check if current can have the reference to desired entities
//        this.factory = current.getFactory();
//        this.productionLine = current.getProductionLine;
    }

    @Override
    public boolean hasNext() {
        return false;

        // TODO
//        if (current.getNext() != null) {
//            return true;
//        }
//        int nextProductionUnitIndex = factory.getProductionLines().indexOf(productionLine) + 1;
//        return nextProductionUnitIndex >= factory.getProductionLines().size();
    }

    @Override
    public ProductionUnit next() {
        return null;

        // TODO
//        if (current.getNext() != null) {
//            return current.getNext();
//        }
//        int nextProductionUnitIndex = factory.getProductionLines().indexOf(productionLine) + 1;
//        if (nextProductionUnitIndex >= factory.getProductionLines().size()) {
//            throw new NoSuchElementException("Iterator cannot return an element because is at end");
//        }
//        this.productionLine = factory.getProductionLines().get(nextProductionUnitIndex);
//        return productionLine.getProductionUnitChain();
    }
}

// ----------------------------- NOTES -----------------------------
// stromové hierarchie entit továrna ->* linka -> *(stroj|robot|člověk nebo výrobek)