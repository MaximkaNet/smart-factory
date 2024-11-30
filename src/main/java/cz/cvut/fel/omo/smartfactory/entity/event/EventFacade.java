package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFacade {
    private final List<OutageEvent> outageEvents = new ArrayList<>();
    private final List<RepairFinishedEvent> repairFinishedEvents = new ArrayList<>();
    private final List<RepairStartedEvent> repairStartedEvents = new ArrayList<>();
    private final Map<Class<?>, List<FactoryEventListener>> eventTypeListenersMap = new HashMap<>();
    private Factory factory;

    public EventFacade(Factory factory) {
        this.factory = factory;
    }

    public void registerAllForEventType(Map<Class<?>, List<FactoryEventListener>> eventTypeListenersMap) {
        eventTypeListenersMap.forEach((eventType, newListeners) ->
                this.eventTypeListenersMap.computeIfAbsent(eventType, k -> new ArrayList<>()).addAll(newListeners));
    }

    private List<FactoryEventListener> getListenersForEventType(Class<?> eventType) {
        return eventTypeListenersMap.getOrDefault(eventType, Collections.emptyList());
    }

    public void addOutageEvent(Integer priority, AbstractManufacturingEntity abstractManufacturingEntity) {
        OutageEvent event = new OutageEvent(priority, abstractManufacturingEntity);
        outageEvents.add(event);
        factory.getRepairmanPool().addOutageEvent(event);
        getListenersForEventType(OutageEvent.class).forEach(listener -> listener.receiveEvent(event));
    }

    public void addRepairFinishedEvent(Repairman repairman, OutageEvent outageEvent) {
        RepairFinishedEvent repairFinishedEvent = new RepairFinishedEvent(outageEvent.getPriority(), repairman, outageEvent);
        repairFinishedEvents.add(repairFinishedEvent);
        getListenersForEventType(RepairFinishedEvent.class).forEach(listener -> listener.receiveEvent(repairFinishedEvent));
    }

    public void addRepairStartedEvent(Repairman repairman, OutageEvent outageEvent) {
        RepairStartedEvent repairStartedEvent = new RepairStartedEvent(outageEvent.getPriority(), repairman, outageEvent);
        repairStartedEvents.add(repairStartedEvent);
        getListenersForEventType(RepairStartedEvent.class).forEach(listener -> listener.receiveEvent(repairStartedEvent));
    }

    // ----------------------------------------------------- GETTING EVENTS -----------------------------------------------------
    public List<FactoryEvent> getSortedEvents() {
        return Stream.of(outageEvents, repairFinishedEvents, repairStartedEvents)
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<FactoryEvent> getEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return Stream.of(outageEvents, repairFinishedEvents, repairStartedEvents)
                .flatMap(List::stream)
                .filter(event -> event.getGeneratedAt().isAfter(from) && event.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<OutageEvent> getOutageEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return outageEvents.stream()
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairFinishedEvent> getRepairFinishedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return repairFinishedEvents.stream()
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairStartedEvent> getRepairStartedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return repairStartedEvents.stream()
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from) && outageEvent.generatedAt.isBefore(to))
                .sorted()
                .collect(Collectors.toList());
    }
}
