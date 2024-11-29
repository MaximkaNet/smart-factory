package cz.cvut.fel.omo.smartfactory.entity.person.state.repairman;

import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

public class WorkingState extends RepairmanState {

    public WorkingState(Repairman repairman) {
        super(repairman);
    }

    @Override
    public void startRepair() {
    }

    @Override
    public void processRepair() {
        context.processRepair();
        if (context.isRepairCompleted()) {
            context.setState(new FinishedState(context));
        }
    }

    @Override
    public void finishRepair() {

    }
}
