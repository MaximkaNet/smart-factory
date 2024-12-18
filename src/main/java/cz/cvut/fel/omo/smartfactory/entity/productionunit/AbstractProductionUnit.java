package cz.cvut.fel.omo.smartfactory.entity.productionunit;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract production unit
 */
@Getter
@Setter
public abstract class AbstractProductionUnit implements ProductionUnit {
    /**
     * The name
     */
    private final String name;

    /**
     * Current product
     */
    private Product subject = null;

    /**
     * Next production unit in chain of responsibility
     */
    private ProductionUnit next = null;

    /**
     * Manufacturing entity state
     */
    private ProductionUnitState state;

    /**
     * Price per usage
     */
    private float costPerUsage = 0.0f;

    /**
     * Available flag
     */
    private boolean isAvailable = true;

    /**
     * Create production unit
     */
    public AbstractProductionUnit(String name, ProductionUnitState initialState) {
        this.name = name;
        this.state = initialState;
    }

    public AbstractProductionUnit(String name) {
        this(name, null);
    }

    @Override
    public boolean accept(Product product) {
        if (product == null || subject != null) {
            return false;
        }
        subject = product;
        return true;
    }

    @Override
    public Product pop() {
        Product product = subject;
        subject = null;
        return product;
    }

    @Override
    public Product peek() {
        return subject;
    }

    @Override
    public void reset() {
        ProductionUnit next = this.next;
        this.next = null;

        // If chain has next reset it
        if (next != null) {
            next.reset();
        }
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    public boolean isReady() {
        return false;
    }

    public boolean isProcessing() {
        return false;
    }

    public boolean isFinished() {
        return false;
    }

}
