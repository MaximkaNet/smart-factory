package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RepairFinishedEvent extends FactoryEvent {
    private OutageEvent outageEvent;
    AbstractFactoryEquipment abstractManufacturingEntity;

    public RepairFinishedEvent(Integer priority, ProductionUnit sender, OutageEvent outageEvent, Instant time) {
        super(priority, sender, time);
        this.outageEvent = outageEvent;
        this.abstractManufacturingEntity = outageEvent.getAbstractManufacturingEntity();
    }
}
