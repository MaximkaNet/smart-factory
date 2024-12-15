package cz.cvut.fel.omo.smartfactory.entity.productionline;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.entity.productionline.productionline.ProductionLineState;
import cz.cvut.fel.omo.smartfactory.entity.productionline.productionline.ReadyState;
import cz.cvut.fel.omo.smartfactory.entity.series.Series;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductionLine implements TickObserver {
    /**
     * Production line priority
     */
    private int priority;

    /**
     * Current configuration (chain of responsibility)
     */
    private ProductionUnit productionUnitChain;

    /**
     * Production state
     */
    private boolean producing = false;

    /**
     * The current produce series
     */
    private Series currentSeries = null;

    /**
     * The factory
     */
    private Factory factory;

    /**
     * Production line state
     */
    private ProductionLineState state = new ReadyState(this);

    /**
     * Create production line
     */
    public ProductionLine(int priority, List<ProductionUnit> productionUnitChain) {
        this.priority = priority;
        // TODO: create chain
    }

    /**
     * Process production
     */
    protected void process(long dt) {
        if (productionUnitChain == null) {
            return;
        }
        Product completedProduct = productionUnitChain.processNext(currentSeries.getProduct(), dt);
        currentSeries.addCompletedProduct(completedProduct);
    }

    /**
     * Initialize series of products
     */
    public boolean apply(Series series, List<ProductionUnit> sequence) {
        createChain(sequence);
        // validate chain of production units
        if (isValidChain(series.getProduct().getSequence())) {
            currentSeries = series;
            return true;
        }
        return false;
    }

    /**
     * Create chain of responsibility
     *
     * @param list sequence of production units
     */
    private void createChain(List<ProductionUnit> list) {
        ProductionUnit current = null;
        for (ProductionUnit unit : list) {
            // Handle first item in chain
            if (current == null) {
                current = unit;
                continue;
            }
            current.setNext(unit);
            current = unit;
        }
    }

    /**
     * Release a series of products from the assembly line
     */
    public Series pop() {
        Series series = currentSeries;
        currentSeries = null;

        // Reset the chain of responsibility, means make production units
        // on this line available for configuration on another lines
        productionUnitChain.reset();

        return series;
    }

    /**
     * Returns true if production unit chain equals chainForCheck
     */
    private boolean isValidChain(String chainForCheck) {
        ProductionUnit current = productionUnitChain;
        StringBuilder actualSequence = new StringBuilder();
        while (current != null) {
            actualSequence.append(current.getDiscriminator());
            current = current.getNext();
        }
        return chainForCheck.toUpperCase().contentEquals(actualSequence);
    }

    /**
     * Returns true if production line is ready to producing
     */
    public boolean isReady() {
        return !producing;
    }

    /**
     * Produce one product.
     * Can run in worker thread (the process may stop due to a malfunction of
     * the robot or machine, which will be waiting for repair)
     */
    @Override
    public void update(long dt) {
        state.process(dt);
    }
}
