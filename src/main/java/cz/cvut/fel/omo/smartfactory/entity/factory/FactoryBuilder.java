package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.builder.Builder;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactoryBuilder implements Builder<Factory> {
    private String name;
    private Integer tickLength;
    private RepairmanPool repairmanPool;
    private List<Person> people = new ArrayList<>();
    private List<Machine> machines;
    private List<Robot> robots;
    private List<ProductionLine> productionLines = new ArrayList<>();
    private List<Behavioral> tactSubscribers = new ArrayList<>();
    private HashMap<Class<? extends FactoryEvent>, List<FactoryEventListener>> eventables = new HashMap<>();

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
        // create worker ...
        return this;
    }

    public FactoryBuilder addRobot(String id, String name, float cost, float health) {
        // create robot ...
        return this;
    }

    public FactoryBuilder addMachine(String id, String name, float cost, float health) {
        // Create machine ...
        return this;
    }

//    public FactoryBuilder setPeople(List<Person> people) {
//        // setting people list
//        this.people = people;
//        // setting tact subscribers
//        this.tactSubscribers.addAll(people);
//        // pooling repairmen into repairmen pool
//        this.repairmanPool = new RepairmanPool(
//                people.stream()
//                        .filter(person -> person instanceof Repairman)
//                        .map(person -> (Repairman) person)
//                        .collect(Collectors.toList())
//        );
//        return this;
//    }
//
//    public FactoryBuilder setProductionLines(List<ProductionLine> productionLines) {
//        this.productionLines = productionLines;
//        return this;
//    }
//
//    public FactoryBuilder setMachines(List<Machine> machines) {
//        this.machines = machines;
//        return this;
//    }
//
//    public FactoryBuilder setRobots(List<Robot> robots) {
//        this.robots = robots;
//        return this;
//    }
//
//    public FactoryBuilder addEventableForEvent(Class<? extends FactoryEvent> eventClass, FactoryEventListener eventable) {
//        eventables.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(eventable);
//        return this;
//    }
//
//    public FactoryBuilder addEventablesForEvent(Map<Class<? extends FactoryEvent>, List<FactoryEventListener>> eventablesMap) {
//        if (eventables == null) {
//            return this;
//        }
//        eventables.putAll(eventablesMap);
//        return this;
//    }

    @Override
    public Factory build() {

        Factory factory = new Factory(this.name, this.tickLength);

        // link people
        // link robots
        // link machines

        // Link behavioral

        // Register event listeners

//        Factory factory = new Factory(name, tickLength, people, machines, robots, productionLines);
//        // set factory attribute on person class
//        people.forEach(person -> person.setFactory(factory));
//        factory.setBehavioralsList(tactSubscribers);
//        factory.setRepairmanPool(repairmanPool);
//        // set eventables in factory facade
//        factory.getEventManager().registerListenersMap(eventables);
//        factory.getEventManager().registerListener(OutageEvent.class, repairmanPool);
//        factory.getBehavioralsList().add(repairmanPool);
//        factory.setEventFacade(new EventFacade(factory.getEventManager()));
//        return factory;

        return factory;
    }
}
