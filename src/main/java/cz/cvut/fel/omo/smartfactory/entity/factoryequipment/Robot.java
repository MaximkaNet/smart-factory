package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventType;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Robot extends AbstractFactoryEquipment {

    protected Robot(String id, Consumption consumption, Price prices) {
        super(id, consumption, prices);
    }

    @Override
    public void process() {
        // process consumption
        // process healthy
        // If repair necessary generate alert event

        FactoryEvent event = new OutageEvent(123, this);
//        event.setTargetId(this.getId());

        if (factory == null) {
            throw new RuntimeException("Robot " + getId() + " is not connect to factory");
        }
//        factory.generateEvent(event);
        factory.getEventManager().notifyListeners(event);
    }
}
