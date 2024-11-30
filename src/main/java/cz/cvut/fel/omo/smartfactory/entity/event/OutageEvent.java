package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import lombok.Getter;

@Getter
public class OutageEvent extends FactoryEvent {
    AbstractManufacturingEntity abstractManufacturingEntity;

    public OutageEvent(Integer priority, AbstractManufacturingEntity abstractManufacturingEntity) {
        super(priority, abstractManufacturingEntity);
        this.abstractManufacturingEntity = abstractManufacturingEntity;
    }

    public void repair(Person person) {
        if (person.getClass() != Repairman.class) {
            return;
        }
        Repairman repairman = (Repairman) person;
        repairman.startRepair(this);
    }

    @Override
    public boolean check(Person person) {
        if (person.getClass() != Repairman.class) {
            return false;
        }
        System.out.println("Repairman: " + person + " checked: " + this);
        setCheckedBy(person);
        isChecked = true;
        return true;
    }
}
