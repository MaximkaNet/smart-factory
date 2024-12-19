package cz.cvut.fel.omo.smartfactory.entity.event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class EventFacade {
    private final EventBus eventManager;

    public EventFacade(EventBus eventManager) {
        this.eventManager = eventManager;
    }

    public List<FactoryEvent> getEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isBefore(from.toInstant()))
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isAfter(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<OutageEvent> getOutageEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof OutageEvent)
                .map(event -> (OutageEvent) event)
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isBefore(from.toInstant()))
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isAfter(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairFinishedEvent> getRepairFinishedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof RepairFinishedEvent)
                .map(event -> (RepairFinishedEvent) event)
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isBefore(from.toInstant()))
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isAfter(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }

    public List<RepairStartedEvent> getRepairStartedEventsFromToSorted(ZonedDateTime from, ZonedDateTime to) {
        return eventManager.getEventHistory().stream()
                .filter(event -> event instanceof RepairStartedEvent)
                .map(event -> (RepairStartedEvent) event)
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isBefore(from.toInstant()))
                .filter(outageEvent -> !outageEvent.getGeneratedAt().isAfter(to.toInstant()))
                .sorted()
                .collect(Collectors.toList());
    }
}
