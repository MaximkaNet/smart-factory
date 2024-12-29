package cz.cvut.fel.omo.smartfactory.repair;

import cz.cvut.fel.omo.smartfactory.event.AbstractEvent;
import cz.cvut.fel.omo.smartfactory.event.EventListener;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Repairmen pool
 */
@Getter
@Setter
public class RepairmenPool implements EventListener, TickObserver {

    /**
     * Identifier factory
     */
    private final IdentifierFactory identifierFactory = new IdentifierFactory("REPAIRMAN");

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
     * Create repairmen pool
     */
    public RepairmenPool() {
        this.repairmanList = new ArrayList<>();
    }

    /**
     * Create and add repairman to pool
     *
     * @param name  The repairman name
     * @param power The repairman factory
     * @return Repairman with unique identifier in this pool
     */
    public Repairman createRepairman(String name, float power) {
        Repairman repairman = new Repairman(identifierFactory.create(name), power);
        repairmanList.add(repairman);
        return repairman;
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
    public void receiveEvent(AbstractEvent event) {
        if (event.getType() == EventType.OUTAGE) {
            outageQueue.add((OutageEvent) event);
        } else {
            throw new RuntimeException("Unsupported event type");
        }
    }
}
