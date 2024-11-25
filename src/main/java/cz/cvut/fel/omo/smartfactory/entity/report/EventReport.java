package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.event.Event;
import cz.cvut.fel.omo.smartfactory.entity.event.Eventable;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventReport extends Report{
    List<Event> events;
    Map<Class<?>, List<Event>> eventTypeMap;
    Map<Eventable, List<Event>> eventSourceMap;
    Map<Optional<Person>, List<Event>> eventCheckerMap;

    public EventReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        super(from, to, factory);

        this.events = factory.getEventFacade().getEventsFromToSorted(from, to);

        this.eventTypeMap = events.stream()
                .collect(Collectors.groupingBy(Event::getClass));

        this.eventSourceMap = events.stream()
                .collect(Collectors.groupingBy(Event::getSender));

        this.eventCheckerMap = events.stream()
                .collect(Collectors.groupingBy(event -> Optional.ofNullable(event.getCheckedBy())));
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "")
                + ", eventTypeMap=" + eventTypeMap + System.lineSeparator()
                + ", eventSourceMap=" + eventSourceMap + System.lineSeparator()
                + ", eventCheckerMap=" + eventCheckerMap + System.lineSeparator();
    }
}
