package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
@Setter
public class RepairmanPool implements FactoryEventListener {
    LinkedBlockingQueue<OutageEvent> outageEventsQueue;
    private List<Repairman> repairmenList;
    private boolean isRunning;

    public RepairmanPool(List<Repairman> repairmenList) {
        this.repairmenList = repairmenList;
        this.outageEventsQueue = new LinkedBlockingQueue<>();
    }

    public void addOutageEvent(OutageEvent outageEvent) {
        if (outageEventsQueue.contains(outageEvent)) {
            return;
        }
        outageEventsQueue.add(outageEvent);
    }

    public synchronized Optional<OutageEvent> getMostUrgentEvent() {
        if (outageEventsQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(outageEventsQueue.poll());
    }

    public void executeRepairs() {
        repairmenList.stream()
                .filter(Repairman::isAvailable)
                .forEach(repairman -> {
                    Optional<OutageEvent> nextEvent = getMostUrgentEvent();
                    nextEvent.ifPresent(outageEvent -> outageEvent.repair(repairman));
                });
    }

    @Override
    public void receiveEvent(FactoryEvent event) {
        if (event instanceof OutageEvent) {
            addOutageEvent((OutageEvent) event);
        } else {
            throw new IllegalArgumentException("Unsupported event type: " + event.getClass());
        }
    }
}
