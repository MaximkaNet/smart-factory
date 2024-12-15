package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.PersonState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.ReadyState;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
abstract public class Person implements TickObserver, FactoryEventListener {
    public static final Logger LOGGER = LogManager.getLogger("Person");

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
        LOGGER.info("Person received: {}", event);
    }
}
