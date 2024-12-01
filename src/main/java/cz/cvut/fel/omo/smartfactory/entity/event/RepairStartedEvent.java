package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

@Getter
public class RepairStartedEvent extends FactoryEvent {
    private OutageEvent outageEvent;
    private AbstractManufacturingEntity abstractManufacturingEntity;

    public RepairStartedEvent(Integer priority, FactoryEventListener sender, OutageEvent outageEvent) {
        super(priority, sender);
        this.outageEvent = outageEvent;
        this.abstractManufacturingEntity = outageEvent.getAbstractManufacturingEntity();
    }

    @Override
    public boolean check(Person person) {
        System.out.println("Person: " + person + " has checked repair started on: ...");
        setCheckedBy(person);
        setChecked(true);
        return true;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", outageEvent=" + outageEvent + "}";
    }
}
