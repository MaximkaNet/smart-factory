package cz.cvut.fel.omo.smartfactory.worker.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;

/**
 * Worker processing state
 */
public final class ProcessingState extends AbstractProductionUnitState {
    /**
     * Create production unit state
     *
     * @param context The context
     */
    public ProcessingState(AbstractProductionUnit context) {
        super(context);
    }

    @Override
    public void process(long dt) {
        context.process(dt);

        if (context.getJobProgress() >= JobUtils.MAX_DURATION) {
            context.setState(new FinishedState(context));
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
        return context.peek();
    }

    @Override
    public boolean repair(float power) {
        return true; // Unit already repaired
    }
}
