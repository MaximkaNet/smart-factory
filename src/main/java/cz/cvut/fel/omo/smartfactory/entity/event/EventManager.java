package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class EventManager {
    private final Map<Class<? extends FactoryEvent>, List<FactoryEventListener>> eventTypeListenersMap = new HashMap<>();
    private final List<FactoryEvent> eventHistory = new ArrayList<>();
    private Factory factory;


    public EventManager(Factory factory) {
        this.factory = factory;
    }

    /**
     * Notify listeners
     *
     * @param event Factory event
     */
    public void notifyListeners(FactoryEvent event) {
        // Notify all registered listeners
        eventTypeListenersMap.getOrDefault(event.getClass(), new ArrayList<>()).forEach((listener) -> listener.receiveEvent(event));
        // Add event to history
        eventHistory.add(event);
    }

    public void registerListenersMap(Map<Class<? extends FactoryEvent>, List<FactoryEventListener>> eventTypeListenersMap) {
        eventTypeListenersMap.forEach((eventType, listeners) -> eventTypeListenersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).addAll(listeners));
    }

    public void registerListeners(Class<? extends FactoryEvent> type, List<FactoryEventListener> eventTypeListenersMap) {
        for (FactoryEventListener listener : eventTypeListenersMap) {
            registerListener(type, listener);
        }
    }

    public void registerListener(Class<? extends FactoryEvent> eventClass, FactoryEventListener listener) {
        eventTypeListenersMap.computeIfAbsent(eventClass, key -> new ArrayList<>()).add(listener);
    }
}
