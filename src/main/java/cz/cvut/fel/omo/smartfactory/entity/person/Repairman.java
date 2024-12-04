package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    private final Float repairPerTick = 0.5f;
    private OutageEvent outageEvent;

    public Repairman(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public synchronized void startRepair(OutageEvent outageEvent) {
        state.work();
        this.outageEvent = outageEvent;
        factory.getEventManager().notifyListeners(new RepairStartedEvent(outageEvent.getPriority(), outageEvent.getAbstractManufacturingEntity(), outageEvent));
    }

    public void finishRepair() {
        factory.getEventManager().notifyListeners(new RepairFinishedEvent(outageEvent.getPriority(), outageEvent.getAbstractManufacturingEntity(), outageEvent));
        System.out.println("Repairman finished");
        state.ready();
    }

    @Override
    public void update(long deltaTime) {
        // checking only for working repairmen
        if (!state.isExecuting()) {
            return;
        }

        float newHealth = outageEvent.getAbstractManufacturingEntity().getActualHealth() + repairPerTick;
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
