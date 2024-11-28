package cz.cvut.fel.omo.smartfactory.entity;

/**
 * Production line component
 */
public interface ProductionUnit {

    /**
     * Get id
     */
    String getId();

    /**
     * Process product production
     */
    void process(Product product);

    /**
     * Set next unit in chain of responsibility
     */
    void setNext(ProductionUnit unit);
}
