package cz.cvut.fel.omo.smartfactory.entity.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person {
    String firstName;
    String lastName;
    String email;
}
