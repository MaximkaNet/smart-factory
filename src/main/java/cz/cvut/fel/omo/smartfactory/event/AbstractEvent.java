package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Abstract immutable factory event
 */
@Getter
@Setter
public abstract class AbstractEvent implements Comparable<AbstractEvent> {

    /**
     * The sender if
     */
    private final Identifier senderId;

    /**
     * Event type
     */
    private final EventType type;

    /**
     * The event priority
     */
    private final int priority;

    /**
     * Generated at
     */
    private final Instant generatedAt;

    /**
     * Create the event
     *
     * @param type        The event type
     * @param senderId    The sender of the Event
     * @param priority    The event priority
     * @param generatedAt The generation time
     */
    public AbstractEvent(EventType type, Identifier senderId, int priority, Instant generatedAt) {
        this.type = type;
        this.senderId = senderId;
        this.priority = priority;
        this.generatedAt = generatedAt;
    }

    @Override
    public int compareTo(AbstractEvent o) {
        int priorityComparison = Integer.compare(o.getPriority(), this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return generatedAt.compareTo(o.getGeneratedAt());
    }
}