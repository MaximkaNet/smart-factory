package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTests {
    @Test
    public void createFactoryUsingFactoryBuilderAndRunIt() {
//        Repairman r1 = new Repairman("1st firstName", "1st lastName", "1st email");
//        Repairman r2 = new Repairman("2nd firstName", "2nd lastName", "2nd email");
//        Repairman r3 = new Repairman("3rd firstName", "3rd lastName", "3rd email");
//        r3.getState().stopWorking(); // one repairman is not working
//        Director director = new Director("Director", "testing", "email@director.som");
//        List<Person> people = new ArrayList<>(Arrays.asList(r1, r2, r3));
//
//        FactoryBuilder builder = new FactoryBuilder();
//        Factory factory = builder
//                .setTickLength(500)
//                .setPeople(people)
//                .addEventableForEvent(OutageEvent.class, director)
//                .build();
//
//        Machine m1 = new Machine("M1", 1.5f, 1);
//        m1.setActualHealth(0);
//        Machine m2 = new Machine("M2", 1.5f, 1);
//        m2.setActualHealth(0);
//        Machine m3 = new Machine("M2", 2.5f, 1);
//        m3.setActualHealth(0);
//        Machine m4 = new Machine("M2", 1.5f, 1);
//        m4.setActualHealth(0);
//        Machine m5 = new Machine("M2", 1.5f, 1);
//        m5.setActualHealth(0);
//        factory.getEventManager().notifyListeners(new OutageEvent(2, m1));
//        factory.getEventManager().notifyListeners(new OutageEvent(3, m2));
//        factory.getEventManager().notifyListeners(new OutageEvent(1, m3));
//        factory.getEventManager().notifyListeners(new OutageEvent(0, m4));
//        factory.getEventManager().notifyListeners(new OutageEvent(0, m5));
//
//        factory.simulate(15);
//
//        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
//        System.out.println(outagesReport);
//        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
//        System.out.println(eventReport);
    }

    @Test
    void createFactoryAndSimulateNTicks() {
        Factory factory = Factory.builder()
                .setName("Factory")
                .setTickLength(200)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("Janko", "Mrkvicka", 5.5f)
                                .addRepairman("Oleh", "Ljashko", 2.5f)
                                .addRepairman("Volodymyr", "Klichko", 10.5f)
                                .build()
                )
                .addWorker("W", "Ethan", "Hawthorne", 100)
                .addWorker("W", "Caleb", "Stroud", 100)
                .addRobot("R", "ForgeX", 15.0f, 300.0f)
                .addRobot("R", "ProdBot 3000", 15.0f, 300.0f)
                .addMachine("M", "Machine 20", 3000.f, 1500.0f)
                .build();

//        Product product = new Product("Skoda");
//        product.setSequence("WRWWMMW");
//
//        factory.getOrderManager().addOrder("Car", product, 100);

        factory.simulate(10);

        assertEquals("Factory", factory.getName());
        assertEquals(200, factory.getTactLengthMilliseconds());
    }
}
