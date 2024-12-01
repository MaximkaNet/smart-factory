package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.FactoryBuilder;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
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

import static org.junit.Assert.assertFalse;

public class FactoryTests {
    @Test
    public void createFactoryUsingFactoryBuilderAndRunIt() {
        Repairman r1 = new Repairman("1st firstName", "1st lastName", "1st email");
        Repairman r2 = new Repairman("2nd firstName", "2nd lastName", "2nd email");
        Repairman r3 = new Repairman("3rd firstName", "3rd lastName", "3rd email");
        r3.getState().stopWorking();
        Director director = new Director("Director", "testing", "email@director.som");
        List<Person> people = new ArrayList<>(Arrays.asList(r1, r2, r3));

        FactoryBuilder builder = new FactoryBuilder("factory 1");
        Factory factory = builder
                .setTactInMilliseconds(500)
                .setPeople(people)
                .addEventableForEvent(OutageEvent.class, director)
                .build();

        Machine m1 = new Machine("M1", 1.5f, 1);
        m1.setHealth(0);
        Machine m2 = new Machine("M2", 1.5f, 1);
        m2.setHealth(0);
        Machine m3 = new Machine("M2", 2.5f, 1);
        m3.setHealth(0);
        Machine m4 = new Machine("M2", 1.5f, 1);
        m4.setHealth(0);
        Machine m5 = new Machine("M2", 1.5f, 1);
        m5.setHealth(0);
        factory.getEventManager().notifyListeners(new OutageEvent(2, m1));
        factory.getEventManager().notifyListeners(new OutageEvent(3, m2));
        factory.getEventManager().notifyListeners(new OutageEvent(1, m3));
        factory.getEventManager().notifyListeners(new OutageEvent(0, m4));
        factory.getEventManager().notifyListeners(new OutageEvent(0, m5));

        factory.simulate(15);

        assertFalse(factory.isRunning());
        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(outagesReport);
        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(eventReport);
    }
}
