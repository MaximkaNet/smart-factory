package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.person.FactoryVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Robot extends AbstractFactoryEquipment {
    public Robot(String id, String name, float pricePerUsage, float healthy) {
        super(id, name, pricePerUsage, healthy);
    }

    @Override
    public void process(long dt) {
        // process consumption
        // process healthy
        // If repair necessary generate alert event

        FactoryEvent event = new OutageEvent(123, this, factory.now());
//        event.setTargetId(this.getId());

        if (factory == null) {
            throw new RuntimeException("Robot " + this.getDiscriminator() + " is not connect to factory");
        }
//        factory.generateEvent(event);
        factory.getEventManager().notifyListeners(event);
    }

    @Override
    public void accept(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
