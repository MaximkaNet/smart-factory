package cz.cvut.fel.omo.smartfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    @Test
    void createAndPop() {
        Product product = new Product("Test product", 10);
        Product destackedProduct = product.pop(5);

        assertEquals("Test product", product.getName());
        assertEquals("Test product", destackedProduct.getName());
        assertEquals(5, product.getCount());
        assertEquals(5, destackedProduct.getCount());
    }

    @Test
    void createAndConsume() {
        Product product = new Product("Test", 10);
        Product destackedProduct = new Product("Test", 5);

        product.consume(destackedProduct);

        assertEquals(15, product.getCount());
        assertEquals(0, destackedProduct.getCount());
    }

    @Test
    void createPopAndConsume() {
        Product product = new Product("Test", 10);
        Product destackedProduct = product.pop(5);

        product.consume(destackedProduct);

        assertEquals("Test", destackedProduct.getName());
        assertEquals(0, destackedProduct.getCount());
        assertEquals(10, product.getCount());
    }
}
