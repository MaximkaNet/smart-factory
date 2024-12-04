package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.person.FactoryVisitor;

/**
 * Production line component
 */
public interface ProductionUnit {

    /**
     * Get id
     */
    String getDiscriminator();

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
     * Accepting visitor
     *
     * @param visitor The factory visitor
     */
    void accept(FactoryVisitor visitor);

    /**
     * Get product after process
     */
    Product pop();

    /**
     * Set next production unit in chain of responsibility
     */
    void setNext(ProductionUnit unit);

    ProductionUnit getNext();

    /**
     * Process chain of responsibility
     *
     * @param product Input product for processing
     * @return Processed product
     */
    Product processNext(Product product);
}
