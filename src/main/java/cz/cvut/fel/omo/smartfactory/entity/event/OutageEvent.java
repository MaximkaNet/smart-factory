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
}
