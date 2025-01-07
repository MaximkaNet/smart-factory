package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import lombok.Getter;

import java.time.Instant;

/**
 * The repair finished event
 */
@Getter
public class RepairFinishedEvent extends AbstractEvent {

    /**
     * Create event
     *
     * @param senderId    The sender id
     * @param generatedAt The generation time
     */
    public RepairFinishedEvent(Identifier senderId, Instant generatedAt) {
        super(EventType.REPAIR_FINISHED, senderId, 0, generatedAt);
    }
}
