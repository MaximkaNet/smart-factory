package cz.cvut.fel.omo.smartfactory.entity.productionunit.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnitState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;

/**
 * Ready production unit state
 */
public class ReadyState extends AbstractProductionUnitState {

    /**
     * Create state
     */
    public ReadyState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        super(context, stateFactory);
    }

    @Override
    public boolean accept(Product product) {
        if (context.accept(product)) {
            context.setState(stateFactory.createProcessingState(context));
        }
        return false;
    }

    @Override
    public void process(long dt) {
        // Nothing
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
