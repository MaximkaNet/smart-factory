package cz.cvut.fel.omo.smartfactory.entity.equipment.equipmentstate;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.AbstractProductionUnitState;

public class ProcessingState extends AbstractProductionUnitState {

    public ProcessingState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        super(context, stateFactory);
    }

    @Override
    public void process(long dt) {
        context.process(dt);

        // Process next
//        ProductionUnit unit = context.getNext();
//        if (unit != null) {
//            unit.getState().process(dt);
//        }
//        context.process(dt);
//        if (context.getActualHealth() <= 0) {
//            context.setState(new BrokenState(context));
//            context.generateOutageEvent();
//        } else if (context.isFinished()) {
//            context.setState(new FinishedState(context));
//        }
    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }

    @Override
    public Product peek() {
        return null;
    }
}
