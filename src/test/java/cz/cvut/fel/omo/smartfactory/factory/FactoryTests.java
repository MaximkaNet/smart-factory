package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
import org.junit.jupiter.api.Test;

public class FactoryTests {
    @Test
    public void createFactoryUsingFactoryBuilderAndRunIt() {

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setTickMs(10)
                .addPerson("repairman", "1. firstName", "1. lastName")
                .addPerson("repairman", "2. firstName", "2. lastName")
                .addPerson("repairman", "3. firstName", "3. lastName")
                .build();

        Director director = new Director(factory, "Director", "testing");
//        factory.addPerson(director);
//        List<Person> people = new ArrayList<>(Arrays.asList(r1, r2, r3));

        // Simulate one tick
        factory.simulate();

//        FactoryBuilder builder = new FactoryBuilder("factory 1");
//        Factory factory = builder
//                .setTactInMilliseconds(500)
//                .setPeople(people)
////                .addEventableForEvent(OutageEvent.class, director)
//                .build();
//
//        factory.startFactory();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("adding events");
//        Machine m1 = new Machine();
//        Machine m2 = new Machine();
//        factory.getEventFacade().addOutageEvent(2, m1);
//        factory.getEventFacade().addOutageEvent(3, m2);
//        factory.getEventFacade().addOutageEvent(1, m1);
//        factory.getEventFacade().addOutageEvent(0, m1);
//        factory.getEventFacade().addOutageEvent(0, m2);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        assertTrue(factory.stopFactory());
//        assertFalse(factory.stopFactory());
//        assertFalse(factory.isRunning());
//
//        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
//        System.out.println(outagesReport);
//        assertEquals(m1, outagesReport.getOutageSourcesSorted().get(0));
//        assertEquals(m2, outagesReport.getOutageSourcesSorted().get(1));
//
//        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
//        System.out.println(eventReport);
    }
}
