package cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

@Getter
@Setter
public class RepairmanPool implements FactoryEventListener, TickObserver {
    private PriorityQueue<OutageEvent> outageEventsQueue;
    private List<Repairman> repairmenList;
    private boolean isRunning;

    public RepairmanPool(List<Repairman> repairmenList) {
        this.repairmenList = repairmenList;
        this.outageEventsQueue = new PriorityQueue<>();
    }

    public void addOutageEvent(OutageEvent outageEvent) {
        outageEventsQueue.add(outageEvent);
    }

    public synchronized Optional<OutageEvent> getMostUrgentEvent() {
        if (outageEventsQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(outageEventsQueue.poll());
    }

    /**
     * Get repairman pool builder
     *
     * @return RepairmanPoolBuilder
     */
    public static RepairmanPoolBuilder builder() {
        return new RepairmanPoolBuilder();
    }

    @Override
    public void update(long deltaTime) {
        repairmenList.stream()
                .filter(repairman -> repairman.getState().isAvailable())
                .forEach(repairman -> {
                    Optional<OutageEvent> nextEvent = getMostUrgentEvent();
                    nextEvent.ifPresent(repairman::startRepair);
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
