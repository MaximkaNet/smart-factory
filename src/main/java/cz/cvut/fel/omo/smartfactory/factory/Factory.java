package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.factory.iterator.TreeIterator;
import cz.cvut.fel.omo.smartfactory.factory.iterator.UsageIterator;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLinePool;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.timer.FactoryTimer;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Basic facade for factory
 */
@Getter
@Setter
public class Factory {
    public static final String TIMER_NAME = "FACTORY_TIMER";
    public static final String EVENTBUS_NAME = "FACTORY_EVENTBUS";

    private static final Logger LOGGER = LogManager.getLogger("Factory");

    /**
     * Factory name
     */
    private String name;

    /**
     * Repair man pool
     */
    private RepairmenPool repairmanPool;

    /**
     * Production Line Pool
     */
    private ProductionLinePool productionLinePool;

    /**
     * Production units
     */
    private ProductionUnitManager productionUnitManager;

    /**
     * Create new factory
     *
     * @param name The factory name
     */
    public Factory(String name) {
        this(name, new RepairmenPool(new ArrayList<>()), new ProductionLinePool(), new ProductionUnitManager());
    }

    /**
     * Create new factory
     *
     * @param name                  The factory name
     * @param repairmanPool         The repairman pool
     * @param productionLinePool    The production line pool
     * @param productionUnitManager The production unit manager
     */
    public Factory(String name, RepairmenPool repairmanPool, ProductionLinePool productionLinePool, ProductionUnitManager productionUnitManager) {
        this.name = name;
        this.productionLinePool = productionLinePool;
        this.productionUnitManager = productionUnitManager;

        this.setRepairmanPool(repairmanPool);
    }

    /**
     * Set repairman pool and register it for outage events
     *
     * @param repairmanPool The repairman pool
     */
    public void setRepairmanPool(RepairmenPool repairmanPool) {
        this.repairmanPool = repairmanPool;
        EventBusManager.getEventBus(Factory.EVENTBUS_NAME).registerListener(EventType.OUTAGE, repairmanPool);
    }

    /**
     * Simulate one factory iteration.
     * Simulation workflow:
     * - Update timer
     * - Check for new orders
     * - Update production lines
     * - Update repairman pool
     */
    public void simulate() {
        FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME);
        // Update timer
        timer.tick();

        if (timer.isNewDay()) {
            LOGGER.info("New day {}", timer.dayNumber());
        }

        LOGGER.info("Time now: {}", timer.now());

        // Update production line pool
        productionLinePool.update(timer.getDeltaTime());
        // Update repairman pool
        repairmanPool.update(timer.getDeltaTime());

        Queue<ProductionLine> completedLines = productionLinePool.getCompletedLines();

        if (!completedLines.isEmpty()) {
            List<AbstractProductionUnit> chain = ProductionLine.reset(completedLines.poll());
            productionUnitManager.restoreSequence(chain);
        }
    }

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
        FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME);
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

    /**
     * Get tree iterator
     *
     * @return Tree Iterator
     */
    public TreeIterator getTreeIterator() {
        return new TreeIterator(productionLinePool.getProductionLineList());
    }

    /**
     * Get usage iterator
     *
     * @return Usage Iterator
     */
    public UsageIterator getUsageIterator() {
        List<AbstractEquipment> equipmentList = new ArrayList<>();

        productionLinePool.getProductionLineList().forEach(line -> {
            AbstractProductionUnit unit = line.getChain();
            while (unit != null) {
                if (unit instanceof AbstractEquipment) {
                    equipmentList.add((AbstractEquipment) unit);
                }
                unit = unit.getNext();
            }
        });
        return new UsageIterator(equipmentList);
    }

    /**
     * Create new order
     *
     * @param name     The order name
     * @param count    Count of products
     * @param sequence The sequence
     */
    public void addOrder(String name, long count, List<String> sequence) {
        List<AbstractProductionUnit> productionUnitSequence = productionUnitManager.convertSequence(sequence);

        ProductionLine line = productionLinePool.createLine("Line of " + name, productionUnitSequence);

        for (long i = 0; i < count; i++) {
            line.addTemplate(new Product(name));
        }
    }
}
