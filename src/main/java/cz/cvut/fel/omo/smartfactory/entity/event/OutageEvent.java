package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import lombok.Getter;

@Getter
public class OutageEvent extends FactoryEvent {
    AbstractFactoryEquipment abstractManufacturingEntity;

    public OutageEvent(Integer priority, AbstractFactoryEquipment abstractManufacturingEntity) {
        super(priority, abstractManufacturingEntity);
        this.abstractManufacturingEntity = abstractManufacturingEntity;
    }

    // TODO: change location of the repair method to somewhere else
    public void repair(Person person) {
        if (person.getClass() != Repairman.class) {
            return;
        }
        Repairman repairman = (Repairman) person;
        repairman.startRepair(this);
    }
}
