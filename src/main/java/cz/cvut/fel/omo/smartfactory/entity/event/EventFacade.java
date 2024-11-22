package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFacade {
    private final List<OutageEvent> outageEvents = new ArrayList<>();
    private final List<RepairFinishedEvent> repairFinishedEvents = new ArrayList<>();
    private final List<RepairStartedEvent> repairStartedEvents = new ArrayList<>();
    private Factory factory;

    public EventFacade(Factory factory) {
        this.factory = factory;
    }

    public void addOutageEvent(Integer priority, AbstractManufacturingEntity abstractManufacturingEntity) {
        OutageEvent event = new OutageEvent(priority, abstractManufacturingEntity);
        outageEvents.add(event);
        factory.getRepairmanPool().addOutageEvent(event);
    }

    public void addRepairFinishedEvent(OutageEvent outageEvent) {
        RepairFinishedEvent repairFinishedEvent = new RepairFinishedEvent(outageEvent.getPriority(), outageEvent);
        repairFinishedEvents.add(repairFinishedEvent);
    }

    public void addRepairStartedEvent(OutageEvent outageEvent) {
        RepairStartedEvent repairStartedEvent = new RepairStartedEvent(outageEvent.getPriority(), outageEvent);
        repairStartedEvents.add(repairStartedEvent);
    }

    public List<Event> getSortedEvents() {
        return Stream.of(outageEvents, repairFinishedEvents, repairStartedEvents)
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Event> getEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
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
