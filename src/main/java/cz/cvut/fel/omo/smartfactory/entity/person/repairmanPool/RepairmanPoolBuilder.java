package cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

import java.util.ArrayList;
import java.util.List;

public class RepairmanPoolBuilder {

    public final List<Repairman> repairmenList = new ArrayList<>();

    public RepairmanPoolBuilder addRepairman(String name, String surname, float power) {
        repairmenList.add(new Repairman(name, surname, power));
        return this;
    }

    public RepairmanPool build() {
        return new RepairmanPool(repairmenList);
    }
}
