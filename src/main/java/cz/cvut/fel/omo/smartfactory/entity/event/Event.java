package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
abstract public class Event {
    protected Integer priority;
    protected ZonedDateTime generatedAt;
    protected AbstractManufacturingEntity abstractManufacturingEntity;
    protected boolean isChecked = false;

    public Event(Integer priority) {
        this.generatedAt = ZonedDateTime.now();
        this.priority = priority;
    }

    abstract public boolean check(Person person);
}
