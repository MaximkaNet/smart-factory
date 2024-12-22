package cz.cvut.fel.omo.smartfactory.repair;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.factory.FactoryTimer;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Repair unit for factory equipments
 */
@Getter
@Setter
public class Repairman {
    public static final Logger LOGGER = LogManager.getLogger("Repairman");

    /**
     * The name
     */
    private final String name;

    /**
     * Repair power
     */
    private final float power;

    /**
     * Event is being processed now
     */
    private OutageEvent outageEvent;

    /**
     * The repair subject
     */
    private AbstractEquipment subject;

    /**
     * The event bus
     */
    private final EventBus eventBus;

    /**
     * Create repairman
     *
     * @param power The repair power per tick
     */
    public Repairman(String name, float power, EventBus eventBus) {
        this.name = name;
        this.power = power;
        this.eventBus = eventBus;
    }

    /**
     * Accept outage event
     *
     * @param event The outage event
     */
    public boolean accept(OutageEvent event) {
        if (outageEvent != null || subject != null) {
            return false;
        }

        outageEvent = event;
        subject = outageEvent.getSender();
        // Generate repair started event
        FactoryTimer timer = eventBus.getTimer();
        eventBus.notifyListeners(new RepairStartedEvent(timer.now()));
        outageEvent.repairStarted(timer.now());
        return true;
    }

    /**
     * Process repairing
     */
    public void process(long deltaTime) {
        if (subject == null) {
            return;
        }

        boolean isRepaired = subject.getState().repair(power * deltaTime);

        if (isRepaired) {
            subject = null;
            outageEvent = null;
            // Generate finished event
            FactoryTimer timer = eventBus.getTimer();
            eventBus.notifyListeners(new RepairFinishedEvent(timer.now()));
            outageEvent.repairFinished(timer.now());
        }
    }
}
