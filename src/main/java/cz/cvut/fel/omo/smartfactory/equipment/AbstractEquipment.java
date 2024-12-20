package cz.cvut.fel.omo.smartfactory.equipment;

import cz.cvut.fel.omo.smartfactory.consumer.ResourceConsumer;
import cz.cvut.fel.omo.smartfactory.equipment.state.ReadyState;
import cz.cvut.fel.omo.smartfactory.event.EventSender;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract factory equipment
 */
@Getter
@Setter
public abstract class AbstractEquipment extends AbstractProductionUnit implements EventSender {

    /**
     * Maximum equipment health
     */
    private final float maximumHealth;

    /**
     * Actual health
     */
    private float actualHealth = 0;

    /**
     * Electricity consumer
     */
    private ResourceConsumer electricityConsumer = new ResourceConsumer();

    /**
     * Oil consumer
     */
    private ResourceConsumer oilConsumer = new ResourceConsumer();

    /**
     * Create factory equipment
     *
     * @param name   The equipment name
     * @param health The maximum health
     */
    public AbstractEquipment(String name, float health) {
        super(name);
        this.maximumHealth = health;

        // Set initial state
        this.setState(new ReadyState(this));
    }

    /**
     * Process repairing
     *
     * @param power The repair power
     */
    public boolean repair(float power) {
        actualHealth += power;

        if (actualHealth >= maximumHealth) {
            actualHealth = maximumHealth;
            usageTime = 0;
            return true;
        }

        return false;
    }

    @Override
    public boolean needRepair() {
        return actualHealth <= 0;
    }
}
