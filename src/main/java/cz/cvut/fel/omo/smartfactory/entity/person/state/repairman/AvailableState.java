package cz.cvut.fel.omo.smartfactory.entity.person.state.repairman;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

public class AvailableState extends RepairmanState {

    public AvailableState(Repairman context) {
        super(context);
    }

    @Override
    public void startRepair() {
        context.startRepair();
        context.setState(new WorkingState(context));
    }

    @Override
    public void processRepair() {

    }

    @Override
    public void finishRepair() {

    }
}
