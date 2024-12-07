package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

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
        if (subject == null) {
            logger.warning(name + " has no subject");
            return;
        }

        actualHealth = -1 * usageTime + maximumHealth;
        usageTime += dt;

        materialConsumer.accept(1);
        oilConsumer.accept(2.3f);
        electricityConsumer.accept(10.5f);
    }

    @Override
    public void accept(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
