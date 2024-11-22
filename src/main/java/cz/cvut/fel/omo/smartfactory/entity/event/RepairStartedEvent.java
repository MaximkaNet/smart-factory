package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

@Getter
public class RepairStartedEvent extends Event{
    private OutageEvent outageEvent;

    public RepairStartedEvent(Integer priority, OutageEvent event) {
        super(priority);
        this.outageEvent = event;
    }

    @Override
    public boolean check(Person person) {
        System.out.println("Person: " + person + " has checked repair started on: ...");
        setChecked(true);
        return true;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", outageEvent=" + outageEvent + "}";
    }
}
