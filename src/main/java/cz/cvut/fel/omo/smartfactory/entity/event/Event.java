package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
abstract public class Event implements Comparable<Event> {
    protected Integer priority;
    protected ZonedDateTime generatedAt;
    protected AbstractManufacturingEntity abstractManufacturingEntity;
    protected boolean isChecked = false;
    protected Person checkedBy;

    public Event(Integer priority) {
        this.generatedAt = ZonedDateTime.now();
        this.priority = priority;
    }

    @Override
    public int compareTo(Event o) {
        int priorityComparison = Integer.compare(o.getPriority(), this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return generatedAt.compareTo(o.getGeneratedAt());
    }

    abstract public boolean check(Person person);

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{priority=" + priority + ", generatedAt=" + generatedAt + ", isChecked=" + isChecked + "}";
    }
}
