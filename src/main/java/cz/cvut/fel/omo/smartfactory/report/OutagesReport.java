package cz.cvut.fel.omo.smartfactory.report;

import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import lombok.Getter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Outages report
 */
@Getter
public class OutagesReport implements Report {
    /**
     * Longest outage time
     */
    private final Duration longestOutageTime;

    /**
     * Shortest outage time
     */
    private final Duration shortestOutageTime;

    /**
     * Average outage time
     */
    private final Duration avgOutageTime;

    /**
     * Average waiting time
     */
    private final Duration avgWaitingTime;

    /**
     * Outage sources sorted
     */
    private final List<String> outageSourcesSorted;

    /**
     * Create outages report
     */
    private OutagesReport(Duration longestOutageTime, Duration shortestOutageTime, Duration avgOutageTime, Duration avgWaitingTime, List<String> outageSourcesSorted) {
        this.longestOutageTime = longestOutageTime;
        this.shortestOutageTime = shortestOutageTime;
        this.avgOutageTime = avgOutageTime;
        this.avgWaitingTime = avgWaitingTime;
        this.outageSourcesSorted = outageSourcesSorted;
    }

    /**
     * Create report using event history from factory event bus
     *
     * @param from Start point
     * @param to   End point
     */
    public static OutagesReport createReport(List<OutageEvent> outageEvents, ZonedDateTime from, ZonedDateTime to) {
        // Get outage events in specified range
        List<OutageEvent> events = outageEvents.stream()
                .filter(event -> event.getGeneratedAt().isAfter(from.toInstant()) &&
                        event.getGeneratedAt().isBefore(to.toInstant()))
                .toList();

        // Create outage report
        return new OutagesReport(
                getLongestOutageTime(events),
                getShortestOutageTime(events),
                getAvgOutageTime(events),
                getAvgWaitingTime(events),
                getOutageSourcesSorted(events)
        );
    }

    /**
     * Get outage sources sorted
     *
     * @param outageEventsList The outage events
     */
    private static List<String> getOutageSourcesSorted(List<OutageEvent> outageEventsList) {
        return outageEventsList.stream()
                .collect(Collectors.toMap(
                        event -> event.getSender().getId().getShortName(),
                        event -> event.getRepairFinishedAt() != null ? Duration.between(event.getGeneratedAt(), event.getRepairFinishedAt()).toMillis() : Duration.ZERO.toMillis(),
                        Long::sum
                ))
                .entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Get average time from event generation time to repair start
     *
     * @param outageEvents The outage events
     */
    private static Duration getAvgWaitingTime(List<OutageEvent> outageEvents) {
        double averageWaitingNanos = outageEvents.stream()
                .map(event -> event.getRepairStartedAt() == null ? Duration.ZERO : Duration.between(event.getGeneratedAt(), event.getRepairStartedAt()))
                .filter(duration -> !duration.isZero())
                .mapToLong(Duration::toNanos)
                .average()
                .orElse(.0);
        return Duration.ofNanos(Math.round(averageWaitingNanos));
    }

    /**
     * Get average time from event generation time to repair finish
     *
     * @param outageEvents The outage events
     */
    private static Duration getAvgOutageTime(List<OutageEvent> outageEvents) {
        double averageWaitingNanos = outageEvents.stream()
                .map(event -> event.getRepairFinishedAt() == null ? Duration.ZERO : Duration.between(event.getGeneratedAt(), event.getRepairFinishedAt()))
                .filter(duration -> !duration.isZero())
                .mapToLong(Duration::toNanos)
                .average()
                .orElse(.0);
        return Duration.ofNanos(Math.round(averageWaitingNanos));
    }

    /**
     * Get the longest outage time
     *
     * @param outageEvents The outage events
     */
    private static Duration getLongestOutageTime(List<OutageEvent> outageEvents) {
        return outageEvents.stream()
                .map(event -> event.getRepairFinishedAt() == null ? Duration.ZERO : Duration.between(event.getGeneratedAt(), event.getRepairFinishedAt()))
                .filter(duration -> !duration.isZero())
                .max(Duration::compareTo)
                .orElse(Duration.ZERO);
    }

    /**
     * Get the shortest outage time
     *
     * @param outageEvents The outage events
     */
    private static Duration getShortestOutageTime(List<OutageEvent> outageEvents) {
        return outageEvents.stream()
                .map(event -> event.getRepairFinishedAt() == null ? Duration.ZERO : Duration.between(event.getGeneratedAt(), event.getRepairFinishedAt()))
                .filter(duration -> !duration.isZero())
                .min(Duration::compareTo)
                .orElse(Duration.ZERO);
    }

    @Override
    public String toString() {
        return "Shortest outage time: " + shortestOutageTime.toString() +
                System.lineSeparator() +
                "Longest outage time: " + longestOutageTime.toString() +
                System.lineSeparator() +
                "Average outage time: " + avgOutageTime.toString() +
                System.lineSeparator() +
                "Average waiting time: " + avgWaitingTime.toString() +
                System.lineSeparator() +
                "Outage sources: " + String.join(", ", outageSourcesSorted) +
                System.lineSeparator();
    }

    @Override
    public String exportJson() {
        return "";
    }
}
