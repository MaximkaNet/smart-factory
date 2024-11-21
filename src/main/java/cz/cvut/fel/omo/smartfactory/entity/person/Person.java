package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.IdleState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person implements TactSubscriber {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected PersonState state;
    protected Integer currentTact;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = new IdleState(this);
        currentTact = 0;
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", state=" + state + '}';
    }

    @Override
    public void onNewTact(int currentTact) {
        this.currentTact = currentTact;
    }
}
