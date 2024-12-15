package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.RunningState;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
public class Repairman extends Person {
    public static final Logger LOGGER = LogManager.getLogger("Repairman");

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
        factory.getEventManager().notifyListeners(new RepairStartedEvent(outageEvent.getPriority(),
                outageEvent.getAbstractManufacturingEntity(),
                outageEvent,
                factory.now()
        ));
        LOGGER.info("Repairman started");
    }

    public void finishRepair() {
        factory.getEventManager().notifyListeners(new RepairFinishedEvent(outageEvent.getPriority(),
                outageEvent.getAbstractManufacturingEntity(),
                outageEvent,
                factory.now()
        ));

        // checking the outage event
        outageEvent.setCheckedBy(this);

        state.ready();

        AbstractFactoryEquipment factoryEquipment = outageEvent.getAbstractManufacturingEntity();

        factoryEquipment.setState(new RunningState(factoryEquipment));
        LOGGER.info("Repairman finished");
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
