package cz.cvut.fel.omo.smartfactory.state.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;

public class RunningState extends FactoryEquipmentState {

    public RunningState(AbstractFactoryEquipment context) {
        super(context);
    }

    @Override
    public void process(long dt) {
        context.process(dt);
        if (context.isFinished()) {
            context.setState(new FinishedState(context));
        }
    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }
}
