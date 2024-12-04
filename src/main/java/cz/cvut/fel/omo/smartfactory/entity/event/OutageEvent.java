package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import lombok.Getter;

import java.time.Instant;

@Getter
public class OutageEvent extends FactoryEvent {
    AbstractFactoryEquipment abstractManufacturingEntity;

    public OutageEvent(Integer priority, AbstractFactoryEquipment abstractManufacturingEntity, Instant time) {
        super(priority, abstractManufacturingEntity, time);
        this.abstractManufacturingEntity = abstractManufacturingEntity;
    }
}
