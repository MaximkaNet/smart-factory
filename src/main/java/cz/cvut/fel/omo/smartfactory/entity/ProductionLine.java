package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.factory.Behavioral;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.state.productionline.ProductionLineState;
import cz.cvut.fel.omo.smartfactory.state.productionline.ReadyState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionLine implements Behavioral {
    /**
     * The unique id
     */
    private final String id;

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

    private ProductionLineState state = new ReadyState(this);

    public ProductionLine(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    /**
     * Initialize series of products
     */
    public boolean applySeries(Series series) {
        // validate chain of production units
        if (isValidChain(series.getProduct().getSequence())) {
            currentSeries = series;
            return true;
        }
        // Line reconfiguration:
        // send request to factory for reconfiguration
        return false;
    }

    /**
     * Returns true if production unit chain equals chainForCheck
     */
    private boolean isValidChain(String chainForCheck) {
        ProductionUnit current = productionUnitChain;
        StringBuilder actualSequence = new StringBuilder();
        while (current != null) {
            actualSequence.append(current.getDiscriminator().charAt(0));
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
    public void update(long dt) {
        if (productionUnitChain == null) {
            return;
        }
        Product completedProduct = productionUnitChain.processNext(currentSeries.getProduct());
        currentSeries.addCompletedProduct(completedProduct);
    }
}
