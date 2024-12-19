package cz.cvut.fel.omo.smartfactory.entity.productionunit;

import cz.cvut.fel.omo.smartfactory.entity.Product;

/**
 * Production line component
 */
public interface ProductionUnit {

    /**
     * Get discrimination character(s)
     */
    String getName();

    /**
     * Process product production
     */
    void process(long dt);

    /**
     * Accept product for processing
     *
     * @return True if product was accepted false otherwise
     */
    boolean accept(Product product);

    /**
     * Get product after process
     */
    Product pop();

    /**
     * Peek the product
     */
    Product peek();

    /**
     * Set next production unit in chain of responsibility
     */
    void setNext(ProductionUnit unit);

    /**
     * Get next unit from chain of responsibility
     */
    ProductionUnit getNext();

    /**
     * Reset chain
     */
    void reset();

    /**
     * Returns true if unit not on the production line
     */
    boolean isAvailable();

    /**
     * Returns true if processing is complete
     */
    boolean isFinished();

    /**
     * Set production unit state
     */
    void setState(ProductionUnitState state);
}
