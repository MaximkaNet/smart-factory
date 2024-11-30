package cz.cvut.fel.omo.smartfactory.entity;

public class Sequence {
    private final ProductionLine productionLine;

    private Product completedProduct = null;
    // Make queue

    public Sequence(ProductionLine productionLine) {
        this.productionLine = productionLine;
    }


    public void add(Product product) {
        // If first production unit ready to work
        // give template to him
    }

    public boolean hasCompletedProduct() {
        return completedProduct != null;
    }

    public Product getCompletedProduct() {
        Product completedProduct = this.completedProduct;
        this.completedProduct = null;
        return completedProduct;
    }

    public void update() {
        // Update all robots
    }
}
