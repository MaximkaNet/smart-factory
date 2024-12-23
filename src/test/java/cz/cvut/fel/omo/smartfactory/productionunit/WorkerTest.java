package cz.cvut.fel.omo.smartfactory.productionunit;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkerTest {
    @Test
    void createAndProcessOneProductAndFinished() {
        Product template = new Product("Test product");

        Identifier workerId = (new IdentifierFactory("WORKER_TEST")).create("Worker");

        AbstractProductionUnit worker = new Worker(workerId, JobUtils.stepDuration(1));

        boolean isAccepted = worker.getState().accept(template);
        worker.getState().process(10);
        Product processed = worker.getState().pop();

        assertTrue(isAccepted);
        assertNotNull(processed);
        assertEquals(template, processed);
        assertEquals("Test product", processed.getName());
    }
}
