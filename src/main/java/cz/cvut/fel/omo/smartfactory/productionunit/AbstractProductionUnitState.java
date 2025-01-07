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
     *
     * @param dt delta time from the factory
     */
    public abstract void process(long dt);

    /**
     * Accept product for processing
     *
     * @param product product to accept
     * @return True if product was accepted false otherwise
     */
    public abstract boolean accept(Product product);

    /**
     * Get product after process
     *
     * @return product popped
     */
    public abstract Product pop();

    /**
     * Peek the product
     *
     * @return product peeked
     */
    public abstract Product peek();

    /**
     * Repair the unit
     *
     * @param power power of repairment
     * @return TRUE if Production unit is repaired
     */
    public abstract boolean repair(float power);
}
