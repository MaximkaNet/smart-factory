package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

@Getter
public class RepairFinishedEvent extends FactoryEvent {
    private OutageEvent outageEvent;
    AbstractManufacturingEntity abstractManufacturingEntity;

    public RepairFinishedEvent(Integer priority, FactoryEventListener sender, OutageEvent outageEvent) {
        super(priority, sender);
        this.outageEvent = outageEvent;
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
