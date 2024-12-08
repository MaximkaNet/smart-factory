package cz.cvut.fel.omo.smartfactory.entity.person.workerState;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;

public final class ProcessState extends WorkerState {

    public ProcessState(Worker context) {
        super(context);
    }

    @Override
    public void process(long deltaTime) {
        context.process(deltaTime);
        if (context.isFinished()) {
            context.setWorkerState(new FinishedState(context));
        }
    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }
}
