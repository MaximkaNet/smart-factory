package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Robot extends AbstractManufacturingEntity {

    protected Robot(String id, Consumption consumption, Price prices) {
        super(id, consumption, prices);
    }

    public void update() {
        // process consumption
        // process healthy
        // If repair necessary generate alert event

        FactoryEvent event = new FactoryEvent(FactoryEventType.ALERT, "Need repair", 123);
        event.setTargetId(this.getId());

        if (factory == null) {
            throw new RuntimeException("Robot " + getId() + " is not connect to factory");
        }
        factory.generateEvent(event);
    }

    @Override
    public void process(Product product) {

    }
}
