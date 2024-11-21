package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.person.personState.IdleState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private PersonState state;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = new IdleState(this);
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", state=" + state + '}';
    }
}
