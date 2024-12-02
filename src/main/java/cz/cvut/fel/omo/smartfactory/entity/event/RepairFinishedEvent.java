package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

@Getter
public class RepairFinishedEvent extends FactoryEvent {
    private OutageEvent outageEvent;
    AbstractFactoryEquipment abstractManufacturingEntity;

    public RepairFinishedEvent(Integer priority, ProductionUnit sender, OutageEvent outageEvent) {
        super(priority, sender);
        this.outageEvent = outageEvent;
        this.abstractManufacturingEntity = outageEvent.getAbstractManufacturingEntity();
    }
}
