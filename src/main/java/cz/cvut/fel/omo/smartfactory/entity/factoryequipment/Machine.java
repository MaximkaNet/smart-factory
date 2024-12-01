package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Machine extends AbstractFactoryEquipment {

    public Machine(String id, float pricePerUsage) {
        super(id, pricePerUsage);
    }

    @Override
    public void process() {
        // Do stuff
    }
}
