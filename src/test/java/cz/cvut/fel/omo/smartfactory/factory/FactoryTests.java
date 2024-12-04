package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.EventReport;
import cz.cvut.fel.omo.smartfactory.entity.report.OutagesReport;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTests {
    @Test
    public void createFactoryUsingFactoryBuilderAndRunIt() throws InterruptedException {
        Factory factory = Factory.builder()
                .setName("Factory 1")
                .setTickLength(250)
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("Janko", "First", .2f)
                                .addRepairman("Janko", "Second", .2f)
                                .addRepairman("Janko", "Third", .2f)
                                .build()
                )
                .addDirector("D", "Adam", "Boss")
                .addInspector("I", "Laco", "Inspector")
                .addMachine("M", "M1", 200f, 1)
                .addMachine("M", "M2", 350f, 1)
                .addMachine("M", "M3", 400f, 1)
                .addMachine("M", "M4", 100f, 1)
                .addMachine("M", "M5", 200f, 1)
                .build();

        // one repairman is not working
        factory.getRepairmanPool().getRepairmenList().get(0).getState().stopWorking();

        factory.getEventManager().notifyListeners(new OutageEvent(2, factory.getMachines().get(0)));
        factory.getEventManager().notifyListeners(new OutageEvent(3, factory.getMachines().get(1)));
        factory.getEventManager().notifyListeners(new OutageEvent(1, factory.getMachines().get(2)));
        factory.getEventManager().notifyListeners(new OutageEvent(0, factory.getMachines().get(3)));
        factory.getEventManager().notifyListeners(new OutageEvent(0, factory.getMachines().get(4)));

        // setting actual health to 0
        for (Machine machine : factory.getMachines()) {
            machine.setActualHealth(0);
        }

        factory.simulateRealtime(15);

        OutagesReport outagesReport = new OutagesReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(outagesReport);
        EventReport eventReport = new EventReport(ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now(), factory);
        System.out.println(eventReport);
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
        assertEquals(200, factory.getTickLengthMillis());
    }
}
