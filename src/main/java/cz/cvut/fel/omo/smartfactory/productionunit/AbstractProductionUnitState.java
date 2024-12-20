package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;

/**
 * Abstract production unit state
 */
public abstract class AbstractProductionUnitState {

    /**
     * The context
     */
    protected final AbstractProductionUnit context;

    /**
     * Create production unit state
     *
     * @param context The context
     */
    public AbstractProductionUnitState(AbstractProductionUnit context) {
        this.context = context;
    }

    /**
     * Process product production
     */
    public abstract void process(long dt);

    /**
     * Accept product for processing
     *
     * @return True if product was accepted false otherwise
     */
    public abstract boolean accept(Product product);

    /**
     * Get product after process
     */
    public abstract Product pop();

    /**
     * Peek the product
     */
    public abstract Product peek();

    /**
     * Repair the unit
     */
    public abstract boolean repair(float power);
}
