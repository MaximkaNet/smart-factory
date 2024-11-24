package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

@Getter
public class RepairFinishedEvent extends Event{
    private OutageEvent outageEvent;

    public RepairFinishedEvent(Integer priority, OutageEvent event) {
        super(priority);
        this.outageEvent = event;
        this.abstractManufacturingEntity = outageEvent.getAbstractManufacturingEntity();
    }

    @Override
    public boolean check(Person person) {
        System.out.println("Person: " + person + " has checked repair finished on: ...");
        setCheckedBy(person);
        setChecked(true);
        return true;
    }
}
