package cz.cvut.fel.omo.smartfactory.entity.equipment.equipmentstate;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.AbstractProductionUnitState;

public class BrokenState extends AbstractProductionUnitState {

    public BrokenState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        super(context, stateFactory);
    }

    @Override
    public void process(long dt) {

    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }

    @Override
    public Product peek() {
        return null;
    }
}
