package cz.cvut.fel.omo.smartfactory.entity.repair;

import cz.cvut.fel.omo.smartfactory.entity.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.entity.equipment.state.AbstractEquipmentState;
import cz.cvut.fel.omo.smartfactory.entity.event.EventBus;
import cz.cvut.fel.omo.smartfactory.entity.event.EventSender;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Repair unit for factory equipments
 */
@Getter
@Setter
public class Repairman implements EventSender {
    public static final Logger LOGGER = LogManager.getLogger("Repairman");

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
    private EventBus eventBus;

    /**
     * Create repairman
     *
     * @param power The repair power per tick
     */
    public Repairman(float power) {
        this.power = power;
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
        subject = (AbstractEquipment) outageEvent.getSender();
        // Generate repair started event
        if (eventBus != null) {
            eventBus.getEventsFactory().generateRepairStartedEvent(this);
            outageEvent.repairStarted(eventBus.getTimer().now());
        }
        return true;
    }

    /**
     * Process repairing
     */
    public void process(long deltaTime) {
        if (subject == null) {
            return;
        }

        AbstractEquipmentState subjectState = (AbstractEquipmentState) subject.getState();

        boolean isRepaired = subjectState.repair(power * deltaTime);

        if (isRepaired) {
            subject = null;
            outageEvent = null;
            // Generate finished event
            if (eventBus != null) {
                eventBus.getEventsFactory().generateRepairFinishedEvent(this);
                outageEvent.repairFinished(eventBus.getTimer().now());
            }
        }
    }
}
