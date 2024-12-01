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
    void process();

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
     * Set next production unit in chain of responsibility
     */
    void setNext(ProductionUnit unit);

    /**
     * Process chain of responsibility
     *
     * @param product Input product for processing
     * @return Processed product
     */
    Product processNext(Product product);
}
