package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.FactoryBuilder;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        OutageEvent e1 = new OutageEvent(2);
        OutageEvent e2 = new OutageEvent(3);
        OutageEvent e3 = new OutageEvent(1);
        OutageEvent e4 = new OutageEvent(0);
        OutageEvent e5 = new OutageEvent(0);

        System.out.println("adding events");
        factory.getRepairmanPool().addOutageEvent(e1);
        factory.getRepairmanPool().addOutageEvent(e2);
        factory.getRepairmanPool().addOutageEvent(e3);
        factory.getRepairmanPool().addOutageEvent(e4);
        factory.getRepairmanPool().addOutageEvent(e5);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(factory.stopFactory());
        assertFalse(factory.stopFactory());
        assertFalse(factory.isRunning());
    }
}
