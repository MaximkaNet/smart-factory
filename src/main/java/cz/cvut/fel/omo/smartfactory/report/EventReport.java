package cz.cvut.fel.omo.smartfactory.report;

import cz.cvut.fel.omo.smartfactory.event.AbstractEvent;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Event report
 */
@Getter
public class EventReport implements Report {
    /**
     * Event types
     */
    private final List<String> eventTypes;

    /**
     * Event sources
     */
    private final List<String> eventSources;

    /**
     * Create event report
     */
    private EventReport(List<String> eventTypes, List<String> eventSources) {
        this.eventTypes = eventTypes;
        this.eventSources = eventSources;
    }

    /**
     * Create event report in specified time range
     */
    public static EventReport createReport(List<AbstractEvent> events, ZonedDateTime from, ZonedDateTime to) {

        List<AbstractEvent> eventsInRange = events.stream()
                .filter(event -> event.getGeneratedAt().isAfter(from.toInstant()) && event.getGeneratedAt().isBefore(to.toInstant()))
                .toList();

        return new EventReport(
                getEventTypes(eventsInRange),
                getSources(eventsInRange)
        );
    }

    /**
     * Get event types
     */
    private static List<String> getEventTypes(List<AbstractEvent> events) {
        return events.stream()
                .map(event -> event.getType().name())
                .distinct()
                .toList();
    }

    /**
     * Get event sources
     */
    private static List<String> getSources(List<AbstractEvent> events) {
        return events.stream()
                .map(event -> event.getSenderId().getShortName())
                .distinct()
                .toList();
    }

    @Override
    public String toString() {
        return "Types: " + String.join(", ", eventTypes) +
                System.lineSeparator() +
                "Sources: " + String.join(", ", eventSources) +
                System.lineSeparator();
    }

    @Override
    public String exportJson() {
        return "";
    }
}
