package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Robot;
import cz.cvut.fel.omo.smartfactory.entity.event.Eventable;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FactoryBuilder {
    private String name;
    private Integer tactInMilliseconds;
    private RepairmanPool repairmanPool;
    private List<Person> people = new ArrayList<>();
    private List<Machine> machines;
    private List<Robot> robots;
    private List<ProductionLine> productionLines = new ArrayList<>();
    private List<TactSubscriber> tactSubscribers = new ArrayList<>();
    private HashMap<Class<?>, List<Eventable>> eventables = new HashMap<>();

    public FactoryBuilder(String name) {
        this.name = name;
    }

    public FactoryBuilder setTactInMilliseconds(Integer tactInMilliseconds) {
        this.tactInMilliseconds = tactInMilliseconds;
        return this;
    }

    public FactoryBuilder setPeople(List<Person> people) {
        // setting people list
        this.people = people;
        // setting tact subscribers
        this.tactSubscribers.addAll(people);
        // pooling repairmen into repairmen pool
        this.repairmanPool = new RepairmanPool(
                people.stream()
                        .filter(person -> person instanceof Repairman)
                        .map(person -> (Repairman) person)
                        .collect(Collectors.toList())
        );
        return this;
    }

    public FactoryBuilder setProductionLines(List<ProductionLine> productionLines) {
        this.productionLines = productionLines;
        return this;
    }

    public FactoryBuilder setMachines(List<Machine> machines) {
        this.machines = machines;
        return this;
    }

    public FactoryBuilder setRobots(List<Robot> robots) {
        this.robots = robots;
        return this;
    }

    public FactoryBuilder addEventableForEvent(Class<?> eventClass, Eventable eventable){
        eventables.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(eventable);
        return this;
    }

    public FactoryBuilder addEventablesForEvent(Map<Class<?>, List<Eventable>> eventablesMap) {
        if (eventables == null) {
            return this;
        }
        eventables.putAll(eventablesMap);
        return this;
    }

    public Factory build() {
        Factory factory = new Factory(name, tactInMilliseconds, people, machines, robots, productionLines);
        // set factory attribute on person class
        people.forEach(person -> person.setFactory(factory));
        factory.setTactSubscribers(tactSubscribers);
        factory.setRepairmanPool(repairmanPool);
        // set eventables in factory facade
        factory.getEventFacade().registerAllForEventType(eventables);
        return factory;
    }
}
