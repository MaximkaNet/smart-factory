package cz.cvut.fel.omo.smartfactory.worker;

import cz.cvut.fel.omo.smartfactory.Material;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.visitor.FactoryVisitor;
import cz.cvut.fel.omo.smartfactory.worker.state.ReadyState;
import lombok.Getter;
import lombok.Setter;

/**
 * The worker
 */
@Setter
@Getter
public final class Worker extends AbstractProductionUnit {

    /**
     * Job step (in percents)
     */
    private final float jobStep;

    /**
     * Create a worker
     *
     * @param id      The worker name
     * @param jobStep The job step (in percents)
     */
    public Worker(Identifier id, float jobStep) {
        super(id);
        this.jobStep = jobStep;
        setState(new ReadyState(this));
    }

    @Override
    public void process(long dt) {
        jobProgress += jobStep;

        this.getUsageConsumer().accept(1.0f);
        this.getMaterialConsumer().accept(new Material("worker", 1.0f, 2));
        // Do something ...
    }

    @Override
    public boolean repair(float power) {
        return true; // Worker not repairable, because always true
    }

    @Override
    public boolean needRepair() {
        return false; // Worker not repairable, because always false
    }

    @Override
    public void acceptVisitor(FactoryVisitor visitor) {
        visitor.visitWorker(this);
    }
}
