package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.builder.Builder;
import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Robot;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventType;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FactoryBuilder implements Builder<Factory> {
    private String name = "unnamed";
    private int tickMs = 100;
    private final List<ProductionLine> lines = new ArrayList<>();
    private final List<Robot> robots = new ArrayList<>();
    private final List<Machine> machines = new ArrayList<>();
    private final List<Person> people = new ArrayList<>();

    public FactoryBuilder() {
    }

    public FactoryBuilder setName(String val) {
        this.name = Objects.requireNonNull(val);
        return this;
    }

    public FactoryBuilder setTickMs(int val) {
        this.tickMs = val;
        return this;
    }

    // TODO abstract person factory
    public FactoryBuilder addPerson(String type, String firstName, String lastName) {
        if (type.equals("worker")) {


        } else if (type.equals("director")) {

        } else if (type.equals("repairman")) {
            Repairman repairman = new Repairman(null, firstName, lastName);
            // Add to people
            // Add to repairman pool
        } else if (type.equals("inspector")) {

        }
        return this;
    }

    public FactoryBuilder addRobot(Robot robot) {
        robots.add(robot);
        return this;
    }

    public FactoryBuilder addMachine(Machine machine) {
        machines.add(machine);
        return this;
    }

    @Override
    public Factory build() {
        Factory factory = new Factory(
                name,
                tickMs,
                people,
                machines,
                robots,
                lines
        );

        // connect robots/machines/people to factory
        for (Person person : people) {
//            person.setFactory(factory);
        }

        for (Robot robot : robots) {
            robot.setFactory(factory);
        }

        for (Machine machine : machines) {
            machine.setFactory(factory);
        }

        for (ProductionLine line : lines) {
//            line.setFactory(factory);
        }

        RepairmanPool pool = new RepairmanPool(factory, new ArrayList<>());

        factory.setRepairmanPool(pool);

        // Register event listener
        factory.getEventManager().registerListener(FactoryEventType.ALERT, pool);

        return factory;
    }

//    public FactoryBuilder setPeople(List<Person> people) {
//        // setting people list
//        this.people = people;
//        // setting tact subscribers
////        this.tactSubscribers.addAll(people);
////        // pooling repairmen into repairmen pool
////        this.repairmanPool = new RepairmanPool(
////                people.stream()
////                        .filter(person -> person instanceof Repairman)
////                        .map(person -> (Repairman) person)
////                        .collect(Collectors.toList())
////        );
//        return this;
//    }
//
//    public Factory build() {
//        Factory factory = new Factory(name);
//        factory.setTactLengthMilliseconds(tactInMilliseconds);
//        factory.setPeople(people);
//        // set factory attribute on person class
//        people.forEach(person -> person.setFactory(factory));
//        factory.setTactSubscribers(tactSubscribers);
//        factory.setRepairmanPool(repairmanPool);
//        // set eventables in factory facade
//        factory.getEventFacade().registerAllForEventType(eventables);
//        return factory;
//    }
}
