package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract production unit
 */
@Getter
@Setter
public abstract class AbstractProductionUnit {
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
    private AbstractProductionUnit next = null;

    /**
     * Manufacturing entity state
     */
    private AbstractProductionUnitState state;

    /**
     * Price per usage
     */
    private final float costPerUsage = 0.0f;

    /**
     * Available flag
     */
    private boolean isAvailable = true;

    /**
     * Is finished flag
     */
    private boolean isFinished = false;

    /**
     * The job progress
     */
    protected float jobProgress = JobUtils.START_POINT;

    /**
     * Create production unit
     */
    public AbstractProductionUnit(String name, AbstractProductionUnitState initialState) {
        this.name = name;
        this.state = initialState;
    }

    public AbstractProductionUnit(String name) {
        this(name, null);
    }

    /**
     * Accept product for processing
     *
     * @return True if product was accepted false otherwise
     */
    public boolean accept(Product product) {
        if (product == null || subject != null) {
            return false;
        }
        jobProgress = JobUtils.START_POINT;
        subject = product;
        return true;
    }

    /**
     * Get product after process
     */
    public Product pop() {
        Product product = subject;
        subject = null;
        return product;
    }

    /**
     * Process product production
     */
    public abstract void process(long dt);

    /**
     * Process chain
     */
    public Product processChain(long dt) {
        if (next == null) {
            return state.pop();
        }
        // Accept
        if (next.state.accept(state.peek())) {
            state.pop();
        }
        next.state.process(dt);

        return next.processChain(dt);
    }

    /**
     * Peek the product
     */
    public Product peek() {
        return subject;
    }

    /**
     * Reset chain
     */
    public void reset() {
        AbstractProductionUnit next = this.next;
        this.next = null;

        // If chain has next reset it
        if (next != null) {
            next.reset();
        }
    }

    /**
     * Repair the production unit
     */
    public abstract boolean repair(float power);

    /**
     * Returns true if actual health <= 0
     */
    public abstract boolean needRepair();
}
