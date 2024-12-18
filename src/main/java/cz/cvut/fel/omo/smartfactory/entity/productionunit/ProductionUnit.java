package cz.cvut.fel.omo.smartfactory.entity.productionunit.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.person.FactoryVisitor;

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

//    /**
//     * Process chain of responsibility
//     *
//     * @param product Input product for processing
//     * @return Processed product
//     */
//    Product processNext(Product product, long dt);

    /**
     * Reset chain
     */
    void reset();

    /**
     * Returns true if unit not on the production line
     */
    boolean isAvailable();

    /**
     * Accepting visitor
     *
     * @param visitor The factory visitor
     */
    void acceptVisitor(FactoryVisitor visitor);
}
