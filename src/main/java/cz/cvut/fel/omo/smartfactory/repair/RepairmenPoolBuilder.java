package cz.cvut.fel.omo.smartfactory.entity.repair;

import java.util.ArrayList;
import java.util.List;

public class RepairmenPoolBuilder {

    public final List<Repairman> repairmenList = new ArrayList<>();

    public RepairmenPoolBuilder addRepairman(String name, String surname, float power) {
        repairmenList.add(new Repairman(name, surname, power));
        return this;
    }

    public RepairmenPool build() {
        return new RepairmenPool(repairmenList);
    }
}
