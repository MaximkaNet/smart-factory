package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person {
    protected String firstName;
    protected String lastName;

    protected final Factory factory;

    public Person(Factory factory, String firstName, String lastName) {
        this.factory = factory;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "firstName=" + firstName + ", lastName=" + lastName + '}';
    }
}
