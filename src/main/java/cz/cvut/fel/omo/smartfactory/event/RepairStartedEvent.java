package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import lombok.Getter;

import java.time.Instant;

/**
 * Repair started event
 */
@Getter
public class RepairStartedEvent extends AbstractEvent {

    /**
     * Create event
     *
     * @param senderId    The sender identifier
     * @param generatedAt The generation time
     */
    public RepairStartedEvent(Identifier senderId, Instant generatedAt) {
        super(EventType.REPAIR_STARTED, senderId, 0, generatedAt);
    }
}
