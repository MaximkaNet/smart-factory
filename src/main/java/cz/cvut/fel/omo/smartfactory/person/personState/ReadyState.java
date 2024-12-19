package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public final class ReadyState extends PersonState {
    public ReadyState(Person person) {
        this.person = person;
    }

    @Override
    public void work() {
        person.setState(new WorkingState(person));
    }

    @Override
    public void ready() {
    }

    @Override
    public void stopWorking() {
        person.setState(new IdleState(person));
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean isExecuting() {
        return false;
    }
}
