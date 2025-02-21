package cz.cvut.fel.omo.smartfactory.worker.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;

/**
 * Worker ready state
 */
public final class ReadyState extends AbstractProductionUnitState {
    /**
     * Create production unit state
     *
     * @param context The context
     */
    public ReadyState(AbstractProductionUnit context) {
        super(context);
    }

    @Override
    public void process(long dt) {
    }

    @Override
    public boolean accept(Product product) {
        if (context.accept(product)) {
            context.setState(new ProcessingState(context));
            return true;
        }
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }

    @Override
    public Product peek() {
        return context.peek();
    }

    @Override
    public boolean repair(float power) {
        return true; // Unit already repaired
    }
}
