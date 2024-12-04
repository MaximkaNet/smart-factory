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
        usageTime += dt;

        final float b = 1.009f;
        final float bendCoefficient = 0.9f;
        float a = 0;
        float c = 0;
        float q = 0;

        // f(x) = -b^(x/k + a) + cx + q
        actualHealth = (float) (Math.pow(-b, (usageTime / bendCoefficient + a)) + c * usageTime + maximumHealth);
        if (actualHealth < 0) {
            actualHealth = 0.0f;

            // Send event
        }
    }

    @Override
    public void accept(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
