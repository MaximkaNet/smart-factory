package cz.cvut.fel.omo.smartfactory.event;

import cz.cvut.fel.omo.smartfactory.factory.FactoryTimer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event manager
 */
@Getter
public class EventBus {

    /**
     * Listeners map
     */
    private final Map<Class<? extends AbstractEvent>, List<EventListener>> eventTypeListenersMap = new HashMap<>();

    /**
     * Event history
     */
    private final List<AbstractEvent> eventHistory = new ArrayList<>();

    /**
     * The factory timer
     */
    private final FactoryTimer timer;

    /**
     * Create event manager
     */
    public EventBus(FactoryTimer timer) {
        this.timer = timer;
    }

    /**
     * Notify listeners
     *
     * @param event Factory event
     */
    public void notifyListeners(AbstractEvent event) {
        // Notify all registered listeners
        eventTypeListenersMap.getOrDefault(event.getClass(), new ArrayList<>()).forEach((listener) -> listener.receiveEvent(event));
        // Add event to history
        eventHistory.add(event);
    }

    /**
     * Register listener for specified event type
     *
     * @param eventType The type of event
     * @param listener  The listener
     */
    public void registerListener(Class<? extends AbstractEvent> eventType, EventListener listener) {
        eventTypeListenersMap.computeIfAbsent(eventType, key -> new ArrayList<>()).add(listener);
    }
}
