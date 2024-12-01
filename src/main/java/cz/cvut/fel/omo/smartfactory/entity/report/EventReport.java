package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class EventReport extends Report{
    private List<FactoryEvent> events;
    private Map<Class<?>, List<FactoryEvent>> eventTypeMap;
    private Map<FactoryEventListener, List<FactoryEvent>> eventSourceMap;
    private Map<Optional<Person>, List<FactoryEvent>> eventCheckerMap;

    public EventReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        super(from, to, factory);

        this.events = factory.getEventManager().getEventsFromToSorted(from, to);

        this.eventTypeMap = events.stream()
                .collect(Collectors.groupingBy(FactoryEvent::getClass));

        this.eventSourceMap = events.stream()
                .collect(Collectors.groupingBy(FactoryEvent::getSender));

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
