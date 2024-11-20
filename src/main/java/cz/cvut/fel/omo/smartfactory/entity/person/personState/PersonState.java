package cz.cvut.fel.omo.smartfactory.entity.person.personState;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

abstract public class PersonState {
    protected Person person;

    abstract public PersonState work();
    abstract public PersonState stopWorking();
}
