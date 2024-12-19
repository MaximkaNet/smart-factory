package cz.cvut.fel.omo.smartfactory.entity.event;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Abstract immutable factory event
 */
@Getter
@Setter
public abstract class FactoryEvent implements Comparable<FactoryEvent> {
    /**
     * Event sender
     */
    private final EventSender sender;

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
     * @param sender      The event sender
     * @param priority    The event priority
     * @param generatedAt The generation time
     */
    public FactoryEvent(EventSender sender, int priority, Instant generatedAt) {
        this.sender = sender;
        this.priority = priority;
        this.generatedAt = generatedAt;
    }

    @Override
    public int compareTo(FactoryEvent o) {
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