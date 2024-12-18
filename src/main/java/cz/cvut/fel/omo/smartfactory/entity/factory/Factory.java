package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.EventManager;
import cz.cvut.fel.omo.smartfactory.entity.memento.Memento;
import cz.cvut.fel.omo.smartfactory.entity.memento.Snapshot;
import cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLineManager;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import cz.cvut.fel.omo.smartfactory.entity.series.OrderManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Getter
@Setter
public abstract class Factory implements Memento {
    private static final Logger LOGGER = LogManager.getLogger("Factory");

    /**
     * Factory name
     */
    private String name;

    /**
     * Factory timer
     */
    private final FactoryTimer timer;

    /**
     * All reports
     */
    private List<Report> reports;


    /**
     * Event manager
     */
    private EventManager eventManager;

    /**
     * Orders manager
     */
    private final OrderManager seriesManager;

    /**
     * Repair man pool
     */
    private RepairmanPool repairmanPool;

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
        this.eventManager = new EventManager(this);
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
}
