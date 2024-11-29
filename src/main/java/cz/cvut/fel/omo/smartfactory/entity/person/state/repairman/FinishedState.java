package cz.cvut.fel.omo.smartfactory.entity.person.state.repairman;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

public class FinishedState extends RepairmanState {

    public FinishedState(Repairman context) {
        super(context);
    }

    @Override
    public void startRepair() {

    }

    @Override
    public void processRepair() {

    }

    @Override
    public void finishRepair() {
        context.finishRepair();
        context.setState(new AvailableState(context));
    }
}
