package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public void registerAllForEventType(Map<Class<? extends FactoryEvent>, List<FactoryEventListener>> eventTypeListenersMap) {
        eventTypeListenersMap.forEach((eventType, newListeners) ->
                this.eventTypeListenersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).addAll(newListeners));
    }

    public void registerForEventType(Class<? extends FactoryEvent> eventClass, FactoryEventListener listener) {
        eventTypeListenersMap.computeIfAbsent(eventClass, key -> new ArrayList<>()).add(listener);
    }

    private List<FactoryEventListener> getListenersForEventType(Class<?> eventType) {
        return eventTypeListenersMap.getOrDefault(eventType, Collections.emptyList());
    }

    // ----------------------------------------------------- ADVANCED GETTERS FOR STATISTICS -----------------------------------------------------
    public List<FactoryEvent> getAllEventsSorted() {
        return eventHistory.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<FactoryEvent> getEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventHistory.stream()
                .filter(event -> event.getGeneratedAt().isAfter(from) && event.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<OutageEvent> getOutageEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventHistory.stream()
                .filter(event -> event instanceof OutageEvent)
                .map(event -> (OutageEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairFinishedEvent> getRepairFinishedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventHistory.stream()
                .filter(event -> event instanceof RepairFinishedEvent)
                .map(event -> (RepairFinishedEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairStartedEvent> getRepairStartedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventHistory.stream()
                .filter(event -> event instanceof RepairStartedEvent)
                .map(event -> (RepairStartedEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }
}
