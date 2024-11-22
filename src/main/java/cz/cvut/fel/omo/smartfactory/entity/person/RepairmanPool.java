package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

@Getter
@Setter
public class RepairmanPool {
    PriorityQueue<OutageEvent> outageEventsQueue;
    private List<Repairman> repairmenList;
    private boolean isRunning;

    public RepairmanPool(List<Repairman> repairmenList) {
        this.repairmenList = repairmenList;
        this.outageEventsQueue = new PriorityQueue<>();
    }

    public void addOutageEvent(OutageEvent outageEvent) {
        if (outageEventsQueue.contains(outageEvent)) {
            return;
        }
        outageEventsQueue.add(outageEvent);
        if (isRunning) {
            executeRepairs();
        }
    }

    public synchronized Optional<OutageEvent> getMostUrgentEvent() {
        if (outageEventsQueue.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(outageEventsQueue.poll());
    }

    public void executeRepairs() {
        isRunning = true;
        while (!outageEventsQueue.isEmpty()) {
            repairmenList.parallelStream()
                    .filter(Repairman::isAvailable)
                    .forEach(repairman -> {
                        Optional<OutageEvent> nextEvent = getMostUrgentEvent();
                        nextEvent.ifPresent(outageEvent -> outageEvent.repair(repairman));
                    });
        }
    }
}
