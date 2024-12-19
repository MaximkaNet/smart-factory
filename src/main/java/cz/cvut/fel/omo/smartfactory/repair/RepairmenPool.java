package cz.cvut.fel.omo.smartfactory.entity.repair;

import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Repairmen pool
 */
@Getter
@Setter
public class RepairmenPool implements FactoryEventListener, TickObserver {
    /**
     * The outage events queue
     */
    private PriorityQueue<OutageEvent> outageQueue = new PriorityQueue<>();

    /**
     * The repairman list
     */
    private List<Repairman> repairmanList;

    /**
     * Create repairman pool
     *
     * @param repairmenList The repairman list
     */
    public RepairmenPool(List<Repairman> repairmenList) {
        this.repairmanList = repairmenList;
    }

    /**
     * Get repairman pool builder
     *
     * @return RepairmanPoolBuilder
     */
    public static RepairmenPoolBuilder builder() {
        return new RepairmenPoolBuilder();
    }

    @Override
    public void update(long deltaTime) {
        for (Repairman repairman : repairmanList) {
            // Check event
            if (repairman.accept(outageQueue.peek())) {
                outageQueue.poll();
            }
            // Process repair
            repairman.process(deltaTime);
        }
    }

    @Override
    public void receiveEvent(FactoryEvent event) {
        outageQueue.add((OutageEvent) event);
    }
}
