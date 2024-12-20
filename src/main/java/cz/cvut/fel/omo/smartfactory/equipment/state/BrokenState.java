package cz.cvut.fel.omo.smartfactory.equipment.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;

public class BrokenState extends AbstractProductionUnitState {
    /**
     * Create production unit state
     *
     * @param context The context
     */
    public BrokenState(AbstractProductionUnit context) {
        super(context);
    }

    @Override
    public void process(long dt) {

    }

    @Override
    public boolean repair(float power) {
        if (context.repair(power)) {
            context.setState(new ProcessingState(context));
            return true;
        }
        return false;
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
        return context.peek();
    }
}
