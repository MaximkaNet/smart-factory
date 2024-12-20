package cz.cvut.fel.omo.smartfactory.worker;

import cz.cvut.fel.omo.smartfactory.Material;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
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
     * @param name    The worker name
     * @param jobStep The job step (in percents)
     */
    public Worker(String name, float jobStep) {
        super(name);
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
}
