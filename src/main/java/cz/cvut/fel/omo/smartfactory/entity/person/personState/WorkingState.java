package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public class WorkingState extends PersonState{
    public WorkingState(Person person) {
        this.person = person;
    }

    @Override
    public PersonState work() {
        return this;
    }

    @Override
    public PersonState stopWorking() {
        return new IdleState(person);
    }
}
