package cz.cvut.fel.omo.smartfactory.equipment;

import cz.cvut.fel.omo.smartfactory.Material;

/**
 * The robot
 */
public final class Robot extends AbstractEquipment {
    /**
     * Create factory equipment
     *
     * @param name   The equipment name
     * @param health The maximum health
     */
    public Robot(String name, float health) {
        super(name, health);
    }

    @Override
    public void process(long dt) {
        setActualHealth(-0.5f * usageTime + getMaximumHealth());
        usageTime += dt;

        this.getUsageConsumer().accept(1.0f); // One usage per call this method
        this.getElectricityConsumer().accept(10.0f); // 10 kW/hour
        this.getOilConsumer().accept(0.2f); // 0.2 liter/hour
        this.getMaterialConsumer().accept(new Material("robot", 1.0f, 1));
    }
}
