package cz.cvut.fel.omo.smartfactory.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepairmanPoolTests {
    RepairmanPool repairmanPool;

    @BeforeEach
    public void setUp() {
        List<Repairman> repairmen = new ArrayList<>();
        Repairman r1 = new Repairman("1st firstName", "1st lastName", "1st email");
        Repairman r2 = new Repairman("2nd firstName", "2nd lastName", "2nd email");
        Repairman r3 = new Repairman("3rd firstName", "3rd lastName", "3rd email");
        r1.setAvailable(true);
        r2.setAvailable(true);
        r3.setAvailable(true);

        repairmen.add(r1);
        repairmen.add(r2);
        repairmen.add(r3);

        repairmanPool = new RepairmanPool(repairmen);
    }

    @Test
    public void repairmanPoolHoldsEventsInRightOrder() {
        OutageEvent e1 = new OutageEvent(2);
        OutageEvent e2 = new OutageEvent(3);
        OutageEvent e3 = new OutageEvent(1);
        OutageEvent e4 = new OutageEvent(0);
        OutageEvent e5 = new OutageEvent(0);

        repairmanPool.addOutageEvent(e1);
        repairmanPool.addOutageEvent(e2);
        repairmanPool.addOutageEvent(e3);
        repairmanPool.addOutageEvent(e4);
        repairmanPool.addOutageEvent(e5);

        assertEquals(e2, repairmanPool.getMostUrgentEvent().get());
        assertEquals(e1, repairmanPool.getMostUrgentEvent().get());
        assertEquals(e3, repairmanPool.getMostUrgentEvent().get());
        assertEquals(e4, repairmanPool.getMostUrgentEvent().get());
        assertEquals(e5, repairmanPool.getMostUrgentEvent().get());
    }

    // TODO: it is not testing anything, this is just for me to see if working correctly
    @Test
    public void workIsScheduledBetweenRepairmen() {
        OutageEvent e1 = new OutageEvent(2);
        OutageEvent e2 = new OutageEvent(3);
        OutageEvent e3 = new OutageEvent(1);
        OutageEvent e4 = new OutageEvent(0);
        OutageEvent e5 = new OutageEvent(0);

        repairmanPool.addOutageEvent(e1);
        repairmanPool.addOutageEvent(e2);
        repairmanPool.addOutageEvent(e3);
        repairmanPool.addOutageEvent(e4);
        repairmanPool.addOutageEvent(e5);

        repairmanPool.executeRepairs();
    }
}
