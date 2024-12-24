package cz.cvut.fel.omo.smartfactory.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Event bus manager
 */
public class EventBusManager {
    /**
     * Buses
     */
    private static final Map<String, EventBus> buses = new HashMap<>();

    /**
     * Get event bus by name
     *
     * @param name The event bus name
     */
    public static EventBus getEventBus(String name) {
        return buses.computeIfAbsent(name, k -> new EventBus());
    }

    /**
     * Clear the event buses
     */
    public static void clear() {
        buses.clear();
    }
}
