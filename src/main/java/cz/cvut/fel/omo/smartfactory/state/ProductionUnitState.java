package cz.cvut.fel.omo.smartfactory.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;

public interface ProductionUnitState {
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
}
