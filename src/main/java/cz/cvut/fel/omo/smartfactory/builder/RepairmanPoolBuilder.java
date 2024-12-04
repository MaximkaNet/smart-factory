package cz.cvut.fel.omo.smartfactory.builder;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;

import java.util.ArrayList;
import java.util.List;

public class RepairmanPoolBuilder implements Builder<RepairmanPool> {

    public final List<Repairman> repairmenList = new ArrayList<>();

    public RepairmanPoolBuilder addRepairman(String name, String surname, float power) {
        repairmenList.add(new Repairman(name, surname, power));
        return this;
    }

    @Override
    public RepairmanPool build() {
        return new RepairmanPool(repairmenList);
    }
}
