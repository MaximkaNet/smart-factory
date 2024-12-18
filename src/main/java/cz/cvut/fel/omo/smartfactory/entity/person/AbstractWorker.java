package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ProductionUnitState;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Worker implements ProductionUnit {

    /**
     * The name
     */
    private String name;

    /**
     * The worker subject
     */
    private Product subject = null;

//    /**
//     * Material storage
//     */
//    private Storage<Material> materialStorage = null;

    /**
     * Next production unit in chain of responsibility
     */
    private ProductionUnit next;

    /**
     * The production unit state
     */
    private ProductionUnitState state = null;

//    /**
//     * Cost per tick
//     */
//    private final float cost;
//
//    private float owingToWorker = 0.0f;
//
//    /**
//     * Was work finished ?
//     */
//    private boolean isFinished = false;


    public Worker(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
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
