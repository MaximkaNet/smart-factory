package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.person.workerState.ReadyState;
import cz.cvut.fel.omo.smartfactory.entity.person.workerState.WorkerState;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Worker extends Person implements ProductionUnit {

    private ProductionUnit next;

    /**
     * Cost per tick
     */
    private final float cost;

    private float owingToWorker = 0.0f;

    /**
     * Worker state
     */
    private WorkerState workerState;

    /**
     * Was work finished ?
     */
    private boolean isFinished = false;

    /**
     * The worker subject
     */
    private Product subject = null;

    public Worker(String discriminator, String firstName, String lastName, float cost) {
        super(discriminator, firstName, lastName);
        this.cost = cost;
        this.workerState = new ReadyState(this);
    }

    @Override
    public String getDiscriminator() {
        return "";
    }

    @Override
    public void process(long dt) {
        // Do stuff ...
        owingToWorker += cost;
        isFinished = true;
    }

    @Override
    public boolean accept(Product product) {
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
    public void setNext(ProductionUnit unit) {
        this.next = unit;
    }

    @Override
    public ProductionUnit getNext() {
        return next;
    }

    @Override
    public Product processNext(Product product, long dt) {
        if (next == null) {
            return product;
        }
        workerState.accept(product);
        workerState.process(dt);
        return processNext(workerState.pop(), dt);
    }

    @Override
    public void reset() {
        
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public void update(long deltaTime) {
        // Nothing ...
    }

    @Override
    public void acceptVisitor(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
