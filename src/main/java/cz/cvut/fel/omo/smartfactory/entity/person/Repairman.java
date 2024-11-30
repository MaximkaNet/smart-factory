package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventType;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.state.repairman.RepairmanState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {

    /**
     * Available flag
     */
    private boolean isAvailable = true;

    /**
     * Target entity for repair
     */
    private AbstractManufacturingEntity target = null;

    /**
     * Repair points
     */
    private final float repairPerTick = 10.0f;

    private RepairmanState state;

    public Repairman(Factory factory, String firstName, String lastName) {
        super(factory, firstName, lastName);
    }

    /**
     * Start repair
     */
    public void startRepair() {
        if (target == null) return;
        FactoryEvent event = new FactoryEvent(FactoryEventType.REPAIR_STARTED, "Repairs started", 1);
        factory.getEventManager().notifyListeners(event);
        isAvailable = false;
    }

    /**
     * Process repair
     */
    public void processRepair() {
        if (target == null) return;
        float currentHealth = target.getHealth();

        currentHealth += repairPerTick;

        if (currentHealth > target.getHealthy()) {
            currentHealth = target.getHealthy();
        }
        target.setHealth(currentHealth);
    }

    /**
     * Send event message
     */
    public void finishRepair() {
        if (target == null) return;
        FactoryEvent event = new FactoryEvent(FactoryEventType.REPAIR_COMPLETED, "Repairs completed", 1);
        factory.getEventManager().notifyListeners(event);
        isAvailable = true;
    }

    /**
     * Returns true if target recovered
     */
    public boolean isRepairCompleted() {
        return target.getHealth() >= target.getHealthy();
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", isAvailable=" + isAvailable + "}";
    }
}
