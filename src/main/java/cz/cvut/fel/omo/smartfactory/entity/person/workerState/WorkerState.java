package cz.cvut.fel.omo.smartfactory.entity.person.workerState;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;

public abstract class WorkerState {
    protected final Worker context;

    protected WorkerState(Worker context) {
        this.context = context;
    }

    public abstract void process(long deltaTime);

    public abstract boolean accept(Product product);

    public abstract Product pop();
}
