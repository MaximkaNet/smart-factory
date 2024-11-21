package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Repairman extends Person {
    private boolean isAvailable;

    public Repairman(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void repair(AbstractManufacturingEntity abstractManufacturingEntity) {
        // TODO: implement repairing just a simulation right now
        // TODO: create RepairStartedEvent
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            e.printStackTrace();
        }
        // TODO: create RepairFinishedEvent
    }

    @Override
    public String toString() {
        return "Repairman{" + super.toString() + ", isAvailable=" + isAvailable + '}';
    }
}
