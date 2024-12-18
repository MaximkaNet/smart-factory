package cz.cvut.fel.omo.smartfactory.entity.equipment;

import cz.cvut.fel.omo.smartfactory.entity.equipment.state.factory.EquipmentStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ReadyState;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract factory equipment
 */
@Getter
@Setter
public abstract class AbstractEquipment extends AbstractProductionUnit {

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

    public boolean isBroken() {
        return false;
    }
}
