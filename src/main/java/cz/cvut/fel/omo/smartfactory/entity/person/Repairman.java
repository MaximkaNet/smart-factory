package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    private boolean isAvailable;
    private Integer willBeFinishedOnTact;
    private AbstractManufacturingEntity repairingEntity;

    public Repairman(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void startRepair(AbstractManufacturingEntity abstractManufacturingEntity) {
        // TODO: create RepairStartedEvent
        willBeFinishedOnTact = currentTact + 3;
        repairingEntity = abstractManufacturingEntity;
    }

    public void finishRepair(){
        // TODO: create RepairFinishedEvent
        System.out.println("Repairman finished");
    }

    @Override
    public void onNewTact(int currentTact){
        super.onNewTact(currentTact);
        if (currentTact >= willBeFinishedOnTact) {
            finishRepair();
        }
    }

    @Override
    public String toString() {
        return "Repairman{" + super.toString() + ", isAvailable=" + isAvailable + '}';
    }
}
