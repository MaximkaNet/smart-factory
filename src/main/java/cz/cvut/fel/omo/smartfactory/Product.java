package cz.cvut.fel.omo.smartfactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    /**
     * The unique product name
     */
    private String name;

    /**
     * Count of products
     */
    private long count;

    /**
     * Create single product
     *
     * @param name The product name
     */
    public Product(String name) {
        this(name, 1);
    }

    /**
     * Create stack of products
     *
     * @param name  The product name
     * @param count The product count
     */
    public Product(String name, long count) {
        this.name = name;
        this.count = count;
    }

    /**
     * Consume the product
     */
    public void consume(Product product) {
        if (product.getName().equals(name)) {
            this.count += product.getCount();
            product.count = 0;
        } else {
            throw new RuntimeException("The products are different");
        }
    }

    /**
     * Get specified number of products from this product
     *
     * @param count Number of product
     */
    public Product pop(long count) {
        if (this.count <= count) {
            return this;
        }
        this.count -= Math.max(0, count);
        return new Product(name, count);
    }
}