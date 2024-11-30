package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private final Product template;

//    private final List<String> sequence;

    /**
     * Number of products must be produced
     */
    private final int count;

    /**
     * Number of products that on production line
     */
    private int inProgress = 0;

    /**
     * Completed products
     */
    private List<Product> completedProducts = new ArrayList<>();

    public Series(String id, Product product, int count) {
        this.id = id;
        this.template = product;
        this.count = count;
    }

    /**
     * Returns true if series has available templates
     */
    public boolean hasTemplates() {
        return completedProducts.size() + inProgress < count;
    }

    /**
     * Get product template
     */
    public Product getTemplate() {
        inProgress++;
        return template.copy();
    }

    /**
     * Add completed product to series
     *
     * @param product completed product
     */
    public void addCompletedProduct(Product product) {
        inProgress--;
        completedProducts.add(product);
    }
}
