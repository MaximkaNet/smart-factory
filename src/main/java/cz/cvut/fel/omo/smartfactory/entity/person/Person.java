package cz.cvut.fel.omo.smartfactory.entity.person;

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
}
