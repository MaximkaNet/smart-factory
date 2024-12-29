package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeIteratorTest {
    @Test
    void createTwoChainsAndIterateAndGetExceptionInTheEnd() {

        Worker worker1 = new Worker(Identifier.of(1), JobUtils.stepDuration(1));
        Worker worker2 = new Worker(Identifier.of(2), JobUtils.stepDuration(1));
        Worker worker3 = new Worker(Identifier.of(3), JobUtils.stepDuration(1));
        Worker worker4 = new Worker(Identifier.of(4), JobUtils.stepDuration(1));

        worker1.setNext(worker2);
        worker2.setNext(worker3);
        worker3.setNext(worker4);

        Robot robot1 = new Robot(Identifier.of(1), 100);
        Robot robot2 = new Robot(Identifier.of(1), 100);
        Robot robot3 = new Robot(Identifier.of(1), 100);

        robot1.setNext(robot2);
        robot2.setNext(robot3);

        List<ProductionLine> productionLines = List.of(
                new ProductionLine(Identifier.of(1, "Production line", "PL"), worker1),
                new ProductionLine(Identifier.of(1, "Production line", "PL"), robot1)
        );

        TreeIterator iterator = new TreeIterator(productionLines);

        assertTrue(iterator.hasNext());
        assertEquals(worker1, iterator.next());
        assertEquals(worker2, iterator.next());
        assertEquals(worker3, iterator.next());
        assertEquals(worker4, iterator.next());
        assertEquals(robot1, iterator.next());
        assertEquals(robot2, iterator.next());
        assertEquals(robot3, iterator.next());
        assertFalse(iterator.hasNext());

        Throwable throwable = assertThrows(NoSuchElementException.class, iterator::next, "NEXT NOT FOUND");
        assertEquals("No production lines available", throwable.getMessage());
    }
}
