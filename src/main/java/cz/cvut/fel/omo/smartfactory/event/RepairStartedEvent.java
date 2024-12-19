package cz.cvut.fel.omo.smartfactory.entity.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public class RepairStartedEvent extends FactoryEvent {

    /**
     * Create event
     *
     * @param sender      The event sender
     * @param priority    The event priority
     * @param generatedAt The generation time
     */
    public RepairStartedEvent(EventSender sender, int priority, Instant generatedAt) {
        super(sender, priority, generatedAt);
    }
}
