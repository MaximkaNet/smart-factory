package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool.RepairmanPool;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FactoryBuilderTest {

    @Test
    public void factoryBuilderSetsRepairmanPoolAsBehavioral() {
        RepairmanPool mockRepairmanPool = mock(RepairmanPool.class);

        Factory factory = Factory.builder()
                .setRepairmanPool(
                        mockRepairmanPool
                )
                .build();

        factory.simulate(10);
        verify(mockRepairmanPool, times(10)).update(anyLong());
    }

    @Test
    public void FactoryBuilderSetsRepairmanToListenToOutageEvent() {
        RepairmanPool mockRepairmanPool = mock(RepairmanPool.class);

        Factory factory = Factory.builder()
                .setRepairmanPool(
                        mockRepairmanPool
                )
                .addMachine("M", "machine", 10, 10)
                .build();

        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(0), Instant.now()));
        factory.getEventManager().notifyListeners(new OutageEvent(2, factory.getMachines().get(0), Instant.now()));
        factory.simulate(5);
        verify(mockRepairmanPool, times(2)).receiveEvent(any());
    }

    @Test
    public void factoryBuilderSetsBehavioralsForAllProductionUnits() {
        Factory factory = Factory.builder()
                .setRepairmanPool(
                        RepairmanPool.builder().build()
                )
                .addWorker("W", "first", "last", 10)
                .addRobot("R", "name", 10, 10)
                .addDirector("D", "first", "last")
                .addInspector("I", "first", "last")
                .addMachine("M", "name", 10, 10)
                .build();

        // 3 ProductionUnits + RepairmanPool
        assertEquals(4, factory.getBehavioralsList().size());
    }
}
