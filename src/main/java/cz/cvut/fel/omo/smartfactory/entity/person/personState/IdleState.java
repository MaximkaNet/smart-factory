package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public class IdleState extends PersonState{
    public IdleState(Person person) {
        this.person = person;
    }

    @Override
    public PersonState work() {
        return new WorkingState(person);
    }

    @Override
    public PersonState stopWorking() {
        return this;
    }
}
