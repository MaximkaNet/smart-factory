package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.IdleState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.ReadyState;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.WorkingState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    private final Float repairPerTick = 0.2f;
    private OutageEvent outageEvent;

    public Repairman(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public synchronized void startRepair(OutageEvent outageEvent) {
        state.work();
        this.outageEvent = outageEvent;
        factory.getEventManager().notifyListeners(new RepairStartedEvent(outageEvent.getPriority(), this, outageEvent));
    }

    public void finishRepair() {
        factory.getEventManager().notifyListeners(new RepairFinishedEvent(outageEvent.getPriority(), this, outageEvent));
        System.out.println("Repairman finished");
        outageEvent.check(this);
        state.ready();
    }

    @Override
    public void onNewTact(int currentTact) {
        super.onNewTact(currentTact);

        // checking only for working repairmen
        if (!state.isExecuting()){
            return;
        }

        float currentHealth = outageEvent.getAbstractManufacturingEntity().getHealth() + repairPerTick;

        if (currentHealth > outageEvent.getAbstractManufacturingEntity().getHealthy()) {
            currentHealth = outageEvent.getAbstractManufacturingEntity().getHealthy();
        }
        outageEvent.getAbstractManufacturingEntity().setHealth(currentHealth);

        if (isRepairCompleted()) {
            finishRepair();
        }
    }

    /**
     * Returns true if target recovered
     */
    public boolean isRepairCompleted() {
        return outageEvent.getAbstractManufacturingEntity().getHealth() >= outageEvent.getAbstractManufacturingEntity().getHealthy();
    }
}
