package cz.cvut.fel.omo.smartfactory.entity;

/**
 * Production line component
 */
public abstract class ProductionLineUnit {
    private ProductionLineUnit next;

    /**
     * Link production line components
     *
     * @param first The first component in line
     * @param chain The chain of components
     * @return The first component
     */
    public static ProductionLineUnit link(ProductionLineUnit first, ProductionLineUnit... chain) {
        ProductionLineUnit head = first;
        for (ProductionLineUnit nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract Product process(Product product);

    /**
     * Process next component.
     *
     * @param processedProduct Processed product from previous ProductLineComponent or blank product
     * @return ManufacturingProduct if next component equal null
     */
    protected Product processNext(Product processedProduct) {
        if (next == null) {
            return processedProduct;
        }
        return next.process(processedProduct);
    }
}
