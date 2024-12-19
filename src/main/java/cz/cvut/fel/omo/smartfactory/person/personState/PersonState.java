package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

abstract public class PersonState {
    protected Person person;

    abstract public void work();

    abstract public void ready();

    abstract public void stopWorking();

    abstract public boolean isAvailable();

    abstract public boolean isExecuting();
}
