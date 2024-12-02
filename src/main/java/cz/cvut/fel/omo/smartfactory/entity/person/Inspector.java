package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

public class Inspector extends Person {
    public Inspector(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public void update(long deltaTime) {

    }
}
