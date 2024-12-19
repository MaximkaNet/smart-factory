package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public final class IdleState extends PersonState {
    public IdleState(Person person) {
        this.person = person;
    }

    @Override
    public void work() {
        throw new IllegalStateException("Person cannot change state to working from idle. Maybe you forgot to make Person ready!");
    }

    @Override
    public void ready() {
        person.setState(new ReadyState(person));
    }

    @Override
    public void stopWorking() {
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isExecuting() {
        return false;
    }
}
