package cz.cvut.fel.omo.smartfactory.entity.equipment;

import cz.cvut.fel.omo.smartfactory.entity.equipment.state.factory.EquipmentStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.event.EventSender;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ReadyState;
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
     * Create factory equipment
     *
     * @param name   The equipment name
     * @param health The maximum health
     */
    public AbstractEquipment(String name, float health) {
        super(name);
        this.maximumHealth = health;

        // Set initial state
        this.setState(new ReadyState(this, new EquipmentStateFactory()));
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
            return true;
        }

        return false;
    }

    public boolean isBroken() {
        return false;
    }
}
