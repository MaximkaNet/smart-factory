package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.entity.person.personState.IdleState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    private Integer willBeFinishedOnTact = -1;
    private Integer repairLengthInTact = 3;
    private OutageEvent outageEvent;
    private boolean isAvailable = true;

    public Repairman(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public synchronized void startRepair(OutageEvent outageEvent) {
        factory.getEventManager().notifyListeners(new RepairStartedEvent(outageEvent.getPriority(), this, outageEvent));
        isAvailable = false;
        willBeFinishedOnTact = currentTact + repairLengthInTact;
        this.outageEvent = outageEvent;
    }

    public void finishRepair() {
        factory.getEventManager().notifyListeners(new RepairFinishedEvent(outageEvent.getPriority(), this, outageEvent));
        System.out.println("Repairman finished");
        outageEvent.check(this);
        isAvailable = true;
    }

    @Override
    public void onNewTact(int currentTact) {
        super.onNewTact(currentTact);
        if (state.getClass() == IdleState.class || willBeFinishedOnTact == -1) {
            return;
        }
        if (currentTact >= willBeFinishedOnTact) {
            willBeFinishedOnTact = -1;
            finishRepair();
        }
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", isAvailable=" + isAvailable + "}";
    }
}
