package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.time.Duration;
import java.time.ZonedDateTime;

@Getter
public class OutagesReport extends Report {
    private Duration shortestOutageTime;
    private Duration longestOutageTime;
    private Duration avgOutageTime;
    private Duration avgWaitingTime;

    public OutagesReport(Factory factory, ZonedDateTime from, ZonedDateTime to) {
        super(factory, from, to);

//        factory.getEventManager().getEventHistory();
    }

    @Override
    public String exportJson() {
        return "";
    }

//    List<AbstractManufacturingEntity> outageSourcesSorted;
//
//    public OutagesReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
//        super(from, to, factory);
//
//        List<OutageEvent> outageEventsList = factory.getEventFacade().getOutageEventsFromToSorted(from, to);
//        List<RepairFinishedEvent> repairFinishedEventsList = factory.getEventFacade().getRepairFinishedEventsFromToSorted(from, to);
//        List<RepairStartedEvent> repairStartedEventsList = factory.getEventFacade().getRepairStartedEventsFromToSorted(from, to);
//
//        // shortest OutageTime
//        calcShortestOutageTime(repairFinishedEventsList);
//
//        // longest OutageTime
//        calcLongestOutageTime(repairFinishedEventsList);
//
//        // avg outage time
//        calcAvgOutageTime(repairFinishedEventsList);
//
//        // avg waiting time
//        calcAvgWaitingTime(repairStartedEventsList);
//
//        // calculating the outage sources and sorting them descending
//        calcOutageSourcesSorted(outageEventsList);
//    }
//
//    private void calcOutageSourcesSorted(List<OutageEvent> outageEventsList) {
//        outageSourcesSorted = outageEventsList.stream()
//                .map(OutageEvent::getAbstractManufacturingEntity)
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet().stream()
//                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }
//
//    private void calcAvgWaitingTime(List<RepairStartedEvent> repairStartedEventsList) {
//        double averageWaitingNanos = repairStartedEventsList.stream()
//                .map(repairFinishedEvent -> {
//                    OutageEvent outageEvent = repairFinishedEvent.getOutageEvent();
//                    if (outageEvent != null) {
//                        return Duration.between(outageEvent.getGeneratedAt(), repairFinishedEvent.getGeneratedAt());
//                    }
//                    return Duration.ZERO;
//                })
//                .filter(duration -> !duration.isZero())
//                .mapToLong(Duration::toNanos)
//                .average()
//                .orElse(.0);
//        avgWaitingTime = Duration.ofNanos(Math.round(averageWaitingNanos));
//    }
//
//    private void calcAvgOutageTime(List<RepairFinishedEvent> repairFinishedEventsList) {
//        double averageOutageNanos = repairFinishedEventsList.stream()
//                .map(repairFinishedEvent -> {
//                    OutageEvent outageEvent = repairFinishedEvent.getOutageEvent();
//                    if (outageEvent != null) {
//                        return Duration.between(outageEvent.getGeneratedAt(), repairFinishedEvent.getGeneratedAt());
//                    }
//                    return Duration.ZERO;
//                })
//                .filter(duration -> !duration.isZero())
//                .mapToLong(Duration::toNanos)
//                .average()
//                .orElse(.0);
//        avgOutageTime = Duration.ofNanos(Math.round(averageOutageNanos));
//    }
//
//    private void calcLongestOutageTime(List<RepairFinishedEvent> repairFinishedEventsList) {
//        longestOutageTime = repairFinishedEventsList.stream()
//                .map(repairFinishedEvent -> {
//                    OutageEvent outageEvent = repairFinishedEvent.getOutageEvent(); // Assuming getter for OutageEvent
//                    if (outageEvent != null) {
//                        return Duration.between(outageEvent.getGeneratedAt(), repairFinishedEvent.getGeneratedAt());
//                    }
//                    return Duration.ZERO;
//                })
//                .filter(duration -> !duration.isZero())
//                .max(Duration::compareTo)
//                .orElse(Duration.ZERO);
//    }
//
//    private void calcShortestOutageTime(List<RepairFinishedEvent> repairFinishedEventsList) {
//        shortestOutageTime = repairFinishedEventsList.stream()
//                .map(repairFinishedEvent -> {
//                    OutageEvent outageEvent = repairFinishedEvent.getOutageEvent(); // Assuming getter for OutageEvent
//                    if (outageEvent != null) {
//                        return Duration.between(outageEvent.getGeneratedAt(), repairFinishedEvent.getGeneratedAt());
//                    }
//                    return Duration.ZERO;
//                })
//                .filter(duration -> !duration.isZero())
//                .min(Duration::compareTo)
//                .orElse(Duration.ZERO);
//    }
//
//    @Override
//    public String toString() {
//        return super.toString().replace("}", "")
//                + ", shortestOutageTime=" + shortestOutageTime.toMillis() + "ms"
//                + ", longestOutageTime=" + longestOutageTime.toMillis() + "ms"
//                + ", avgOutageTime=" + avgOutageTime.toMillis() + "ms"
//                + ", avgWaitingTime=" + avgWaitingTime.toMillis() + "ms"
//                + ", outageSourcesSorted:" + System.lineSeparator()
//                + outageSourcesSorted.stream()
//                .map(Object::toString)
//                .collect(Collectors.joining(System.lineSeparator()))
//                + System.lineSeparator() + "}";
//    }
}
