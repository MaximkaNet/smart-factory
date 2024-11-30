package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.WorkingState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person implements TactSubscriber, FactoryEventListener {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected PersonState state;
    protected Integer currentTact;
    protected Factory factory;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = new WorkingState(this);
        currentTact = 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", state=" + state + '}';
    }

    @Override
    public void onNewTact(int currentTact) {
        this.currentTact = currentTact;
    }

    @Override
    public void receiveEvent(FactoryEvent event) {
        System.out.println("Person received: " + event);
    }
}
