package cz.cvut.fel.omo.smartfactory.entity.event;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactoryEventManager {
    private final Map<String, List<FactoryEventListener>> listeners = new HashMap<>();

    @Getter
    private final List<FactoryEvent> eventHistory = new ArrayList<>();

    /**
     * Register listener
     */
    public void registerListener(String eventType, FactoryEventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        listeners.get(eventType).add(listener);
    }

    /**
     * Unregister listener
     */
    public void unregisterListener(String eventType, FactoryEventListener listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        listeners.get(eventType).remove(listener);
    }

    /**
     * Notify listeners
     *
     * @param event Factory event
     */
    public void notifyListeners(FactoryEvent event) {
        // Notify all registered listeners
        listeners.getOrDefault(event.getType(), new ArrayList<>()).forEach((listener) -> listener.onEvent(event));
        // Add event to history
        eventHistory.add(event);
    }
}
