package cz.cvut.fel.omo.smartfactory.entity.productionunit.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnitState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;

public class FinishedState extends AbstractProductionUnitState {

    public FinishedState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
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
        Product out = context.pop();
        context.setState(stateFactory.createReadyState(context));
        return out;
    }

    @Override
    public Product peek() {
        return context.peek();
    }
}
