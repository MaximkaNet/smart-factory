package cz.cvut.fel.omo.smartfactory.entity.person.workerState;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;

public final class FinishedState extends WorkerState {

    public FinishedState(Worker context) {
        super(context);
    }

    @Override
    public void process(long deltaTime) {
        // Nothing
    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        Product product = context.pop();
        context.setWorkerState(new ReadyState(context));
        return product;
    }
}
