package cz.cvut.fel.omo.smartfactory.entity.person.workerState;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;

public final class ReadyState extends WorkerState {

    public ReadyState(Worker context) {
        super(context);
    }

    @Override
    public void process(long deltaTime) {
        // nothing ...
    }

    @Override
    public boolean accept(Product product) {
        if (context.accept(product)) {
            context.setWorkerState(new ProcessState(context));
            return true;
        }
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }
}
