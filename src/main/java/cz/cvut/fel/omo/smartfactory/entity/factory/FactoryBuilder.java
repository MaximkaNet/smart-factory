package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FactoryBuilder {
    private String name;
    private Integer tactInMilliseconds;
    private RepairmanPool repairmanPool;
    private List<Person> people = new ArrayList<>();
    private List<TactSubscriber> tactSubscribers = new ArrayList<>();

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

    public Factory build() {
        Factory factory = new Factory(name);
        factory.setTactLengthMilliseconds(tactInMilliseconds);
        factory.setPeople(people);
        factory.setTactSubscribers(tactSubscribers);
        factory.setRepairmanPool(repairmanPool);
        return factory;
    }
}
