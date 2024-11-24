package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.FactoryBuilder;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.report.EventReport;
import cz.cvut.fel.omo.smartfactory.entity.report.OutagesReport;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FactoryTests {
    @Test
    public void createFactoryUsingFactoryBuilderAndRunIt() {
        Repairman r1 = new Repairman("1st firstName", "1st lastName", "1st email");
        Repairman r2 = new Repairman("2nd firstName", "2nd lastName", "2nd email");
        Repairman r3 = new Repairman("3rd firstName", "3rd lastName", "3rd email");
        List<Person> people = new ArrayList<>(Arrays.asList(r1, r2, r3));

        FactoryBuilder builder = new FactoryBuilder("factory 1");
        Factory factory = builder.setTactInMilliseconds(500).setPeople(people).build();

        factory.startFactory();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("adding events");
        Machine m1 = new Machine();
        Machine m2 = new Machine();
        factory.getEventFacade().addOutageEvent(2, m1);
        factory.getEventFacade().addOutageEvent(3, m2);
        factory.getEventFacade().addOutageEvent(1, m1);
        factory.getEventFacade().addOutageEvent(0, m1);
        factory.getEventFacade().addOutageEvent(0, m2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(factory.stopFactory());
        assertFalse(factory.stopFactory());
        assertFalse(factory.isRunning());

        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(outagesReport);
        assertEquals(m1, outagesReport.getOutageSourcesSorted().get(0));
        assertEquals(m2, outagesReport.getOutageSourcesSorted().get(1));

        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(eventReport);
    }
}
