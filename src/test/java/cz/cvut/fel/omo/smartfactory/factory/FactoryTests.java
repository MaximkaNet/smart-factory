package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.FactoryBuilder;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
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
        Director director = new Director("Director", "testing", "email@director.som");
        List<Person> people = new ArrayList<>(Arrays.asList(r1, r2, r3));

        FactoryBuilder builder = new FactoryBuilder("factory 1");
        Factory factory = builder
                .setTactInMilliseconds(500)
                .setPeople(people)
                .addEventableForEvent(OutageEvent.class, director)
                .build();


        Machine m1 = new Machine();
        Machine m2 = new Machine();
        factory.getEventManager().notifyListeners(new OutageEvent(2, m1));
        factory.getEventManager().notifyListeners(new OutageEvent(3, m2));
        factory.getEventManager().notifyListeners(new OutageEvent(1, m1));
        factory.getEventManager().notifyListeners(new OutageEvent(0, m1));
        factory.getEventManager().notifyListeners(new OutageEvent(0, m2));

        factory.simulate(10);

        assertFalse(factory.isRunning());
        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(outagesReport);
        assertEquals(m1, outagesReport.getOutageSourcesSorted().get(0));
        assertEquals(m2, outagesReport.getOutageSourcesSorted().get(1));

        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(eventReport);
    }
}
