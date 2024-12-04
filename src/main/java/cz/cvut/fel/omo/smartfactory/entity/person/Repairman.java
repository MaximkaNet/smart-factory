package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    /**
     * Repair power
     */
    private final float power;

    /**
     * Event is being processed now
     */
    private OutageEvent outageEvent;

    public Repairman(String firstName, String lastName, float power) {
        super(firstName, lastName, "");
        this.power = power;
    }

    public synchronized void startRepair(OutageEvent outageEvent) {
        state.work();
        this.outageEvent = outageEvent;
        factory.getEventManager().notifyListeners(new RepairStartedEvent(outageEvent.getPriority(), outageEvent.getAbstractManufacturingEntity(), outageEvent));
        System.out.println("Repairman started");
    }

    public void finishRepair() {
        factory.getEventManager().notifyListeners(new RepairFinishedEvent(outageEvent.getPriority(), outageEvent.getAbstractManufacturingEntity(), outageEvent));
        state.ready();
        System.out.println("Repairman finished");
    }

    @Override
    public void update(long deltaTime) {
        // checking only for working repairmen
        if (!state.isExecuting()) {
            return;
        }

        float newHealth = outageEvent.getAbstractManufacturingEntity().getActualHealth() + power;
        float cappedHealth = Math.min(newHealth, outageEvent.getAbstractManufacturingEntity().getMaximumHealth());
        outageEvent.getAbstractManufacturingEntity().setActualHealth(cappedHealth);

        if (isRepairCompleted()) {
            finishRepair();
        }
    }

    /**
     * Returns true if target recovered
     */
    public boolean isRepairCompleted() {
        return outageEvent.getAbstractManufacturingEntity().getActualHealth() >= outageEvent.getAbstractManufacturingEntity().getMaximumHealth();
    }
}
