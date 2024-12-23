package cz.cvut.fel.omo.smartfactory.helpers;

import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierManager;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;

import java.util.List;

public class ChainUtils {
    /**
     * Create chain
     */
    public static AbstractProductionUnit createChain(List<AbstractProductionUnit> unitList) {
        AbstractProductionUnit first = null;
        AbstractProductionUnit current = null;

        for (AbstractProductionUnit unit : unitList) {
            if (first == null) {
                first = unit;
                current = unit;
                continue;
            }
            current.setNext(unit);
            current = unit;
        }
        return first;
    }

    public static List<AbstractProductionUnit> getWorkerListWithOneStepEach() {
        IdentifierFactory identifierFactory = IdentifierManager.getFactory("WORKER_TEST");
        return List.of(
                new Worker(identifierFactory.create("Worker 1"), JobUtils.stepDuration(1)),
                new Worker(identifierFactory.create("Worker 2"), JobUtils.stepDuration(1)),
                new Worker(identifierFactory.create("Worker 3"), JobUtils.stepDuration(1)),
                new Worker(identifierFactory.create("Worker 4"), JobUtils.stepDuration(1))
        );
    }
}
