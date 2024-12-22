package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

/**
 * Outage event
 */
@Getter
public class OutageEvent extends AbstractEvent {

    private static final Logger LOGGER = LogManager.getLogger("OutageEvent");

    /**
     * Outage sender
     */
    private final AbstractEquipment sender;

    /**
     * Repair started at
     */
    private Instant repairStartedAt;

    /**
     * Repair finished at
     */
    private Instant repairFinishedAt;

    /**
     * Create outage event
     *
     * @param equipment The sender
     * @param priority  The priority
     * @param time      Generated time
     */
    public OutageEvent(AbstractEquipment equipment, int priority, Instant time) {
        super(priority, time);
        this.sender = equipment;
    }

    /**
     * Repair started event
     *
     * @param time The generation time of the RepairStartedEvent
     */
    public void repairStarted(Instant time) {
        if (this.repairStartedAt != null) {
            LOGGER.warn("Repair already started");
            return;
        }
        this.repairStartedAt = time;
    }

    /**
     * Repair finished event
     *
     * @param time The generation time of the RepairFinishedEvent
     */
    public void repairFinished(Instant time) {
        if (this.repairFinishedAt != null) {
            LOGGER.warn("Repair already finished");
            return;
        }
        this.repairFinishedAt = time;
    }
}
