package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RepairmanPoolTest {

    @Test
    public void repairmanPoolAssignsWorkOnlyToAvailableRepairmen() {
        Factory factory = Factory.builder()
                .addMachine("M", "machine", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("n1", "s", 5)
                                .addRepairman("n2", "s", 4)
                                .addRepairman("n3", "s", 3)
                                .build()
                )
                .build();

        factory.getRepairmanPool().getRepairmenList().get(0).getState().stopWorking();
        factory.getMachines().get(0).setActualHealth(0);
        factory.getMachines().get(1).setActualHealth(0);
        factory.getMachines().get(2).setActualHealth(0);

        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(0), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(1), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(2), Instant.now()));

        factory.simulate(1);

        assertNull(factory.getRepairmanPool().getRepairmenList().get(0).getOutageEvent());
        assertFalse(factory.getRepairmanPool().getRepairmenList().get(0).getState().isExecuting());
        assertNotNull(factory.getRepairmanPool().getRepairmenList().get(1).getOutageEvent());
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(1).getState().isExecuting());
        assertNotNull(factory.getRepairmanPool().getRepairmenList().get(2).getOutageEvent());
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(2).getState().isExecuting());
    }

    @Test
    public void onceRepairmanFreeIsAssignedToOutage() {
        Factory factory = Factory.builder()
                .addMachine("M", "machine", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("n1", "s", 5)
                                .build()
                )
                .build();

        factory.getMachines().get(0).setActualHealth(0);
        factory.getMachines().get(1).setActualHealth(0);
        factory.getMachines().get(2).setActualHealth(0);

        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(0), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(1), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(2), Instant.now()));

        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isExecuting());
        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isAvailable());
        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isExecuting());
        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isAvailable());
        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isExecuting());
        factory.simulate(1);
        assertTrue(factory.getRepairmanPool().getRepairmenList().get(0).getState().isAvailable());

    }

    @Test
    public void outagesAreRepairedInOrderOfPriorityDescending() {
        Factory factory = Factory.builder()
                .addMachine("M", "machine", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .addMachine("M", "machine2", 10, 10)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("n1", "s", 5)
                                .build()
                )
                .build();

        factory.getMachines().get(0).setActualHealth(0);
        factory.getMachines().get(1).setActualHealth(0);
        factory.getMachines().get(2).setActualHealth(0);

        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(0), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(3, factory.getMachines().get(1), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(2, factory.getMachines().get(2), Instant.now()));

        factory.simulate(1);
        assertEquals(Integer.valueOf(3), factory.getRepairmanPool().getRepairmenList().get(0).getOutageEvent().getPriority());
        factory.simulate(2);
        assertEquals(Integer.valueOf(2), factory.getRepairmanPool().getRepairmenList().get(0).getOutageEvent().getPriority());
        factory.simulate(2);
        assertEquals(Integer.valueOf(1), factory.getRepairmanPool().getRepairmenList().get(0).getOutageEvent().getPriority());
    }
}
