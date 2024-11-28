package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Series {
    /**
     * The unique id of the series
     */
    private final String id;

    /**
     * The 'template' of product
     */
    private final Product product;

    /**
     * Number of products must be produced
     */
    private final int count;

    /**
     * Actual number of produced products
     */
    private int produced = 0;

    public Series(String id, Product product, int count) {
        this.id = id;
        this.product = product;
        this.count = count;
    }

    /**
     * Increment produced counter
     */
    public void newProducedProduct() {
        produced++;
    }
}
