package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public final class WorkingState extends PersonState {
    public WorkingState(Person person) {
        this.person = person;
    }

    @Override
    public void work() {
    }

    @Override
    public void ready() {
        person.setState(new ReadyState(person));
    }

    @Override
    public void stopWorking() {
        person.setState(new IdleState(person));
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isExecuting() {
        return true;
    }
}
