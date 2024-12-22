package cz.cvut.fel.omo.smartfactory.equipment;

import cz.cvut.fel.omo.smartfactory.Material;
import cz.cvut.fel.omo.smartfactory.event.EventBus;

/**
 * The machine
 */
public final class Machine extends AbstractEquipment {
    /**
     * Create factory equipment
     *
     * @param name   The equipment name
     * @param health The maximum health
     */
    public Machine(String name, float health, EventBus eventBus) {
        super(name, health, eventBus);
    }

    @Override
    public void process(long dt) {
        setActualHealth(-0.2f * usageTime + getMaximumHealth());
        usageTime += dt;

        this.getUsageConsumer().accept(1.0f); // One usage per call this method
        this.getElectricityConsumer().accept(20.0f); // 20 kW/hour
        this.getOilConsumer().accept(1.0f); // 1 liter/hour
        this.getMaterialConsumer().accept(new Material("machine", 1.0f, 5));
    }
}
