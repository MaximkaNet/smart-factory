package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.builder.Builder;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
import cz.cvut.fel.omo.smartfactory.entity.person.Inspector;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.person.Worker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class FactoryBuilder implements Builder<Factory> {
    private String name = "Default Factory";
    private Integer tickLength = 500;
    private Instant foundationDate = Instant.now();
    private RepairmanPool repairmanPool;
    private List<Person> people = new ArrayList<>();
    private List<Machine> machines = new ArrayList<>();
    private List<Robot> robots = new ArrayList<>();

    public FactoryBuilder() {
    }

    public FactoryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FactoryBuilder setTickLength(Integer tickLength) {
        this.tickLength = tickLength;
        return this;
    }

    public FactoryBuilder setRepairmanPool(RepairmanPool pool) {
        this.repairmanPool = pool;
        return this;
    }

    public FactoryBuilder addWorker(String id, String firstName, String lastName, float cost) {
        people.add(new Worker(id, firstName, lastName, cost));
        return this;
    }

    public FactoryBuilder addDirector(String id, String firstName, String lastName) {
        people.add(new Director(id, firstName, lastName));
        return this;
    }

    public FactoryBuilder addInspector(String id, String firstName, String lastName) {
        people.add(new Inspector(id, firstName, lastName));
        return this;
    }

    public FactoryBuilder addRobot(String id, String name, float cost, float health) {
        robots.add(new Robot(id, name, cost, health));
        return this;
    }

    public FactoryBuilder addMachine(String id, String name, float cost, float health) {
        machines.add(new Machine(id, name, cost, health));
        return this;
    }

    public FactoryBuilder setFoundationDate(LocalDate of) {
        this.foundationDate = of.atStartOfDay(ZoneId.of("UTC")).toInstant();
        return this;
    }

    @Override
    public Factory build() {

        Factory factory = new Factory(this.name, this.tickLength, this.foundationDate);

        // link people
        people.forEach(person -> person.setFactory(factory));
        factory.setRepairmanPool(repairmanPool);
        factory.setPeople(people);
        repairmanPool.getRepairmenList().forEach(repairman -> repairman.setFactory(factory));

        // link robots
        factory.setRobots(robots);

        // link machines
        factory.setMachines(machines);

        // Link behavioral
        people.forEach(person -> factory.getBehavioralsList().add(person));
        robots.forEach(robot -> factory.getBehavioralsList().add(robot));
        machines.forEach(machine -> factory.getBehavioralsList().add(machine));
        factory.getBehavioralsList().add(repairmanPool);
        repairmanPool.getRepairmenList().forEach(repairman -> factory.getBehavioralsList().add(repairman));

        // Register event listeners
        factory.getEventManager().registerListener(OutageEvent.class, repairmanPool);

        return factory;
    }
}
