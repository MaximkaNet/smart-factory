package cz.cvut.fel.omo.smartfactory.equipment.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;

/**
 * Finished state for Equipment
 */
public final class FinishedState extends AbstractProductionUnitState {
    /**
     * Create production unit state
     *
     * @param context The context
     */
    public FinishedState(AbstractProductionUnit context) {
        super(context);
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
        context.setState(new ReadyState(context));
        return context.pop();
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
