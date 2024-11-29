package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

public class Worker extends Person {
    public Worker(Factory factory, String firstName, String lastName) {
        super(factory, firstName, lastName);
    }
}
