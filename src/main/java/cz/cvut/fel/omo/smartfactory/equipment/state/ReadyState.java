package cz.cvut.fel.omo.smartfactory.equipment.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;

/**
 * Ready state for Equipment
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
        return null;
    }

    @Override
    public boolean repair(float power) {
        return true; // Unit already repaired
    }
}
