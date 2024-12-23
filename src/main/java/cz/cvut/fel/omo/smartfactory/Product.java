package cz.cvut.fel.omo.smartfactory;

import lombok.Getter;
import lombok.Setter;

/**
 * The product
 */
@Getter
@Setter
public class Product {
    /**
     * The unique product name
     */
    private final String name;

    /**
     * Create single product
     *
     * @param name The product name
     */
    public Product(String name) {
        this.name = name;
    }
}