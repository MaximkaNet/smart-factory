package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

import java.time.ZonedDateTime;

public class EventReport extends Report {
    public EventReport(Factory factory, ZonedDateTime from, ZonedDateTime to) {
        super(factory, from, to);

        // Generate report
    }

    @Override
    public String exportJson() {
        return "";
    }
//    List<FactoryEvent> events;
//    Map<Class<?>, List<FactoryEvent>> eventTypeMap;
//    Map<Eventable, List<FactoryEvent>> eventSourceMap;
//    Map<Optional<Person>, List<FactoryEvent>> eventCheckerMap;
//
//    public EventReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
//        super(from, to, factory);
//
//        this.events = factory.getEventFacade().getEventsFromToSorted(from, to);
//
//        this.eventTypeMap = events.stream()
//                .collect(Collectors.groupingBy(FactoryEvent::getClass));
//
//        this.eventSourceMap = events.stream()
//                .collect(Collectors.groupingBy(FactoryEvent::getSender));
//
//        this.eventCheckerMap = events.stream()
//                .collect(Collectors.groupingBy(event -> Optional.ofNullable(event.getCheckedBy())));
//    }
}
