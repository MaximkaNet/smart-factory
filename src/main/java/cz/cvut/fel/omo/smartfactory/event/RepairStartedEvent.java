package cz.cvut.fel.omo.smartfactory.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class RepairStartedEvent extends AbstractEvent {

    /**
     * Create event
     *
     * @param generatedAt The generation time
     */
    public RepairStartedEvent(Instant generatedAt) {
        super(0, generatedAt);
    }
}
