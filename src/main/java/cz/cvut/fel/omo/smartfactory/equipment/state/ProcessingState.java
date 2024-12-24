package cz.cvut.fel.omo.smartfactory.equipment.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnitState;
import cz.cvut.fel.omo.smartfactory.timer.FactoryTimer;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;

/**
 * Processing state for Equipment
 */
public final class ProcessingState extends AbstractProductionUnitState {
    /**
     * Create production unit state
     *
     * @param context The context
     */
    public ProcessingState(AbstractProductionUnit context) {
        super(context);
    }

    @Override
    public void process(long dt) {
        context.process(dt);

        if (context.needRepair()) {
            context.setState(new BrokenState(context));

            AbstractEquipment equipment = (AbstractEquipment) context;
            
            EventBus eventBus = EventBusManager.getEventBus(Factory.EVENTBUS_NAME);
            FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME);

            eventBus.notifyListeners(new OutageEvent(equipment, 1, timer.now()));
            return;
        }

        if (context.getJobProgress() >= JobUtils.MAX_DURATION) {
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

    @Override
    public Product peek() {
        return context.peek();
    }

    @Override
    public boolean repair(float power) {
        return true; // Unit already repaired
    }
}
