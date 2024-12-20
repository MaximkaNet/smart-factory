package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.memento.Memento;
import cz.cvut.fel.omo.smartfactory.memento.Snapshot;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLineManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.series.OrderManager;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic facade for factory
 */
@Getter
@Setter
public abstract class Factory implements Memento {
    /**
     * Factory name
     */
    private String name;

    /**
     * Factory timer
     */
    private final FactoryTimer timer;

    /**
     * Event manager
     */
    private EventBus eventBus;

    /**
     * Orders manager
     */
    private final OrderManager seriesManager;

    /**
     * Repair man pool
     */
    private RepairmenPool repairmanPool;

    /**
     * Production Line Pool
     */
    private ProductionLineManager productionLinePool;

    /**
     * Create new factory
     *
     * @param name  The factory name
     * @param timer The factory timer
     */
    protected Factory(String name, FactoryTimer timer) {
        this.name = name;
        this.timer = timer;
        this.eventBus = new EventBus(timer);
        this.seriesManager = new OrderManager(this);
    }

    /**
     * Simulate one factory iteration
     */
    public abstract void simulate();

    /**
     * Simulate number of iterations
     *
     * @param ticks Number of iterations
     */
    public synchronized void simulate(int ticks) {
        for (long i = 0; i < ticks; i++) {
            simulate();
        }
    }

    /**
     * Realtime simulation
     *
     * @param ticks number of simulations
     */
    public void simulateRealtime(int ticks) throws InterruptedException {
        for (long i = 0; i < ticks; i++) {
            simulate();
            Thread.sleep(timer.getRealtimeTickDelay());
        }
    }

    /**
     * Get factory builder
     *
     * @return Factory builder
     */
    public static FactoryBuilder builder() {
        return new FactoryBuilder();
    }

    @Override
    public Snapshot save() {
        // Walk through all attributes of this factory
        return null;
    }

    @Override
    public void restore(Snapshot snapshot) {
        // Nothing...
    }
}
