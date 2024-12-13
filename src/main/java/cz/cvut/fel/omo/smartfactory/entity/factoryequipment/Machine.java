package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.person.FactoryVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Machine extends AbstractFactoryEquipment {

    public Machine(String id, String name, float pricePerUsage, float healthy) {
        super(id, name, pricePerUsage, healthy);
    }

    @Override
    public void process(long dt) {
        if (subject == null) {
            LOGGER.warning(name + " has no subject");
            return;
        }

        actualHealth = -0.5f * usageTime + maximumHealth;
        usageTime += dt;

        materialConsumer.accept(5);
        oilConsumer.accept(12.3f);
        electricityConsumer.accept(100.5f);
    }

    @Override
    public void acceptVisitor(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
