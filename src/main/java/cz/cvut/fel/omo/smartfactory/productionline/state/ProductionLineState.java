package cz.cvut.fel.omo.smartfactory.productionline.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;

/**
 * Production line abstract state
 */
public abstract class ProductionLineState {

    /**
     * The production line
     */
    protected final ProductionLine context;

    /**
     * Create production line state
     */
    protected ProductionLineState(ProductionLine context) {
        this.context = context;
    }

    /**
     * Accept product template for production
     *
     * @param template The product template
     */
    public abstract boolean accept(Product template);

    /**
     * Process production
     *
     * @param deltaTime The delta time
     */
    public abstract void process(long deltaTime);

    /**
     * Remove the released product from production line
     */
    public abstract Product pop();

    /**
     * Returns the product without removing it from the production line
     */
    public abstract Product peek();
}
