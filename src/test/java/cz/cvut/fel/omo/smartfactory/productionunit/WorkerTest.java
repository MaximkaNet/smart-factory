package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierManager;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkerTest {
    @Test
    void createAndProcessOneProductAndFinished() {
        Product templates = new Product("Test product", 10);

        Identifier workerId = IdentifierManager.getFactory("WORKER_TEST").create("Worker");

        AbstractProductionUnit worker = new Worker(workerId, JobUtils.stepDuration(1));

        boolean isAccepted = worker.getState().accept(templates.pop(1));
        worker.getState().process(10);
        Product processed = worker.getState().pop();

        assertTrue(isAccepted);
        assertNotNull(processed);
        assertEquals(1, processed.getCount());
        assertEquals("Test product", processed.getName());
    }
}
