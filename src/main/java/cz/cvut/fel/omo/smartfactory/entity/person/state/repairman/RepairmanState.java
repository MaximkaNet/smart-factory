package cz.cvut.fel.omo.smartfactory.entity.person.state.repairman;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

public abstract class RepairmanState {

    protected final Repairman context;

    protected RepairmanState(Repairman context) {
        this.context = context;
    }

    public abstract void startRepair();

    public abstract void processRepair();

    public abstract void finishRepair();
}
