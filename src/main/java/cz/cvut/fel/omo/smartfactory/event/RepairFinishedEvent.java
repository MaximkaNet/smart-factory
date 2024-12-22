package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RepairFinishedEvent extends AbstractEvent {

    /**
     * Create event
     *
     * @param generatedAt The generation time
     */
    public RepairFinishedEvent(Identifier senderId, Instant generatedAt) {
        super(EventType.REPAIR_FINISHED, senderId, 0, generatedAt);
    }
}
