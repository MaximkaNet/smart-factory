package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventListener;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.state.repairman.RepairmanState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.PriorityQueue;

@Getter
@Setter
public class RepairmanPool implements FactoryEventListener {

    /**
     * Outage events
     */
    private PriorityQueue<FactoryEvent> outageEvents = new PriorityQueue<>();

    /**
     * Repairman list
     */
    private List<Repairman> repairmanList;

    private final Factory factory;

    public RepairmanPool(Factory factory, List<Repairman> repairmanList) {
        this.factory = factory;
        this.repairmanList = repairmanList;
    }

    /**
     * Get available repairman
     */
    public Repairman getAvailableRepairman() {
        for (Repairman repairman : repairmanList) {
            if (repairman.isAvailable()) {
                return repairman;
            }
        }
        return null;
    }

    /**
     * Main repairman pool method
     */
    public void update() {
        if (!outageEvents.isEmpty()) {
            // Get most urgent event
            Repairman repairman = getAvailableRepairman();
            if (repairman != null) {
                FactoryEvent event = outageEvents.poll();
                AbstractManufacturingEntity entity = factory.getManufacturingEntity(event.getTargetId());
                repairman.setTarget(entity);
            }
        }
        // Play with repairman state
        for (Repairman repairman : repairmanList) {
            RepairmanState state = repairman.getState();
            // Call state methods
            state.startRepair();
            state.processRepair();
            state.finishRepair();
        }
    }

    public void onEvent(FactoryEvent event) {
        outageEvents.add(event);
    }
}
