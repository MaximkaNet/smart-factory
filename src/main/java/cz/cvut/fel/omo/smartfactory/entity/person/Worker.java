package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;

public class Worker extends Person{
    public Worker(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }
}
