package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.OrderManager;
import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.event.EventFacade;
import cz.cvut.fel.omo.smartfactory.entity.event.EventManager;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryUsageIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.OutagesReport;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Factory {
    /**
     * Factory name
     */
    private String name;

    /**
     * Tick length
     */
    private Integer tickLengthMillis;

    /**
     * All reports
     */
    private List<Report> reports;

    /**
     * Current tick
     */
    private Integer currentTact = 0;

    /**
     * People
     */
    private List<Person> people;

    /**
     * Robots
     */
    private List<Robot> robots;

    /**
     * Machines
     */
    private List<Machine> machines;


    /**
     * Event manager
     */
    private EventManager eventManager;

    /**
     * Event facade
     */
    private EventFacade eventFacade;

    /**
     * Orders manager
     */
    private final OrderManager orderManager;

    /**
     * Production lines
     */
    private List<ProductionLine> productionLines;

    /**
     * Repair man pool
     */
    private RepairmanPool repairmanPool;

    /**
     * Behavioral list
     */
    private List<Behavioral> behavioralsList = new ArrayList<>();

    public Factory(String name, int tactLengthMilliseconds) {
        this.name = name;
        this.tickLengthMillis = tactLengthMilliseconds;
        this.eventManager = new EventManager(this);
        this.orderManager = new OrderManager(this);
        this.eventFacade = new EventFacade(this.eventManager);
    }

    /**
     * Add series of product
     *
     * @param product Product for producing
     * @param count   count of products
     */
    public void addSeries(Product product, int count) {

    }

    /**
     * Simulate one iteration
     */
    public synchronized void simulate() {
        // run everything that needs to be run on one tact
        // send message about new tact for each tact subscriber
        behavioralsList.forEach(subscriber -> subscriber.update(tickLengthMillis));
    }

    /**
     * Simulate number of iterations
     *
     * @param ticks Number of iterations
     */
    public synchronized void simulate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            System.out.println("tact: " + i);
            simulate();
        }
    }

    /**
     * Realtime simulation
     *
     * @param ticks number of simulations
     */
    public void simulateRealtime(int ticks) throws InterruptedException {
        for (int i = 0; i < ticks; i++) {
            System.out.println("tact: " + i);
            simulate();
            Thread.sleep(tickLengthMillis);
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
     * Create outage report in a time span
     *
     * @return stringify report
     */
    public OutagesReport createOutageReport(ZonedDateTime from, ZonedDateTime to) {
        return new OutagesReport(from, to, this);
    }

    public FactoryTreeIterator getFactoryTreeIterator() {
        return new FactoryTreeIterator(this);
    }

    public FactoryUsageIterator getFactoryUsageIterator() {
        return new FactoryUsageIterator(this);
    }

    /**
     * This method will be used in memento pattern
     *
     * @param date restore date
     */
    public void restore(String date) {
        // TODO: restoring of the factory in memento pattern
    }

    /**
     * Conduct an inspection of the factory
     */
    public void inspect() {
        // TODO: inspector will iterate through factory and inspection will be performed
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{"
                + "name=" + name
                + ", tactLengthMilliseconds=" + tickLengthMillis
                + ", currentTact=" + currentTact + "}";
    }
}
