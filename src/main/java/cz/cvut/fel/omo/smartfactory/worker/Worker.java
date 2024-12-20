package cz.cvut.fel.omo.smartfactory.worker;

import cz.cvut.fel.omo.smartfactory.equipment.state.ReadyState;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
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
