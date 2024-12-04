package cz.cvut.fel.omo.smartfactory.entity.event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EventFacade {
    private final EventManager eventManager;

    public EventFacade(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public List<FactoryEvent> getEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event.getGeneratedAt().isAfter(from.toInstant()) && event.generatedAt.isBefore(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<OutageEvent> getOutageEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof OutageEvent)
                .map(event -> (OutageEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from.toInstant()) && outageEvent.generatedAt.isBefore(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairFinishedEvent> getRepairFinishedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof RepairFinishedEvent)
                .map(event -> (RepairFinishedEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from.toInstant()) && outageEvent.generatedAt.isBefore(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairStartedEvent> getRepairStartedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof RepairStartedEvent)
                .map(event -> (RepairStartedEvent) event)
                .filter(outageEvent -> outageEvent.getGeneratedAt().isAfter(from.toInstant()) && outageEvent.generatedAt.isBefore(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }
}
