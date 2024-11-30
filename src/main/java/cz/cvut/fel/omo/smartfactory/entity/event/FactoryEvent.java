package cz.cvut.fel.omo.smartfactory.entity.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactoryEvent implements Comparable<FactoryEvent> {
    private final FactoryEventType type;
    private final String message;
    private int priority;
    private final long timestamp;
    private String targetId = null;

    public FactoryEvent(FactoryEventType type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(FactoryEvent o) {
        int priorityComparison = Integer.compare(o.getPriority(), this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }

        return Long.compare(timestamp, o.timestamp);
    }
}