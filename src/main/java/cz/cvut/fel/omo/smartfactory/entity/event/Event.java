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
    protected boolean isChecked = false;
    protected Person checkedBy;
    protected Eventable sender;

    public Event(Integer priority, Eventable sender) {
        this.generatedAt = ZonedDateTime.now();
        this.priority = priority;
        this.sender = sender;
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
        return this.getClass().getSimpleName()
                + "{priority=" + priority
                + ", generatedAt=" + generatedAt
                + ", isChecked=" + isChecked
                + ", checkedBy=" + checkedBy
                + ", sender=" + sender
                + "}";
    }
}

// functional req:
// Komunikace mezi stroji, roboty a lidmi probíhá pomocí eventů.
// Event může dostat 1 až N entit (člověk, stroj, robot),
// které jsou na daný druh eventu zaregistrované.
// Eventy je potřeba odbavit.