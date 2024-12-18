package cz.cvut.fel.omo.smartfactory.entity.productionunit.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnitState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;

public class ProcessingState extends AbstractProductionUnitState {

    public ProcessingState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        super(context, stateFactory);
    }

    @Override
    public void process(long dt) {
        context.process(dt);

        if (context.isFinished()) {
            context.setState(stateFactory.createFinishedState(context));
        }
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
