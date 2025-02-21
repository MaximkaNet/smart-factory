package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.CommonMaterial;
import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.consumer.MaterialConsumer;
import cz.cvut.fel.omo.smartfactory.consumer.ResourceConsumer;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.visitor.Visitable;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract production unit
 */
@Getter
@Setter
public abstract class AbstractProductionUnit implements Visitable {
    /**
     * The identifier
     */
    private final Identifier id;

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
     * The usage time
     */
    protected long usageTime = 0;

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
     * The material consumer
     */
    private final MaterialConsumer materialConsumer = new MaterialConsumer(new CommonMaterial());

    /**
     * The usage consumer (just counter)
     */
    private final ResourceConsumer usageConsumer = new ResourceConsumer();

    /**
     * Create production unit
     *
     * @param id           The production unit identifier
     * @param initialState The initial state of the production unit
     */
    public AbstractProductionUnit(Identifier id, AbstractProductionUnitState initialState) {
        this.id = id;
        this.state = initialState;
    }

    /**
     * Create abstract production unit
     *
     * @param id The production unit identifier
     */
    public AbstractProductionUnit(Identifier id) {
        this(id, null);
    }

    /**
     * Accept product for processing
     *
     * @param product product to accept
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
     *
     * @return product that was popped
     */
    public Product pop() {
        Product product = subject;
        subject = null;
        return product;
    }

    /**
     * Process product production
     *
     * @param dt delta time from the factory
     */
    public abstract void process(long dt);

    /**
     * Process chain
     *
     * @param dt delta time from the factory
     * @return Released product
     */
    public Product processChain(long dt) {
        this.state.process(dt);

        if (next == null) {
            return this.state.pop();
        }
        if (this.next.state.accept(this.state.peek())) {
            this.state.pop();
        }
        return this.next.processChain(dt);
    }

    /**
     * Peek the product
     *
     * @return subject peeked
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
     *
     * @param power power of repairment
     * @return TRUE if repair is completed, FALSE otherwise
     */
    public abstract boolean repair(float power);

    /**
     * Returns true if actual health less or equals zero
     *
     * @return true if it needs repair
     */
    public abstract boolean needRepair();
}
