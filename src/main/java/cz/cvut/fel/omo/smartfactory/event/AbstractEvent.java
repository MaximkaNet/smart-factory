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
     * Create event
     *
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

// functional req:
// Komunikace mezi stroji, roboty a lidmi probíhá pomocí eventů.
// Event může dostat 1 až N entit (člověk, stroj, robot),
// které jsou na daný druh eventu zaregistrované.
// Eventy je potřeba odbavit.