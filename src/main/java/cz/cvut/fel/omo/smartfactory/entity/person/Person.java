package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.Behavioral;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.ReadyState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Person implements Behavioral, FactoryEventListener {
    String discriminator;
    protected String firstName;
    protected String lastName;
    protected PersonState state;
    protected Factory factory;

    public Person(String discriminator, String firstName, String lastName) {
        this.discriminator = discriminator;
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = new ReadyState(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "firstName=" + firstName + ", lastName=" + lastName + ", state=" + state + '}';
    }

    @Override
    public void receiveEvent(FactoryEvent event) {
        System.out.println("Person received: " + event);
    }
}
