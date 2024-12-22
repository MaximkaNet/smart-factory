package cz.cvut.fel.omo.smartfactory.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class RepairFinishedEvent extends AbstractEvent {

    /**
     * Create event
     *
     * @param generatedAt The generation time
     */
    public RepairFinishedEvent(Instant generatedAt) {
        super(0, generatedAt);
    }
}
