package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.EventFacade;
import cz.cvut.fel.omo.smartfactory.entity.event.EventManager;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryUsageIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;
import cz.cvut.fel.omo.smartfactory.entity.person.Director;
import cz.cvut.fel.omo.smartfactory.entity.person.Inspector;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.report.OutagesReport;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import cz.cvut.fel.omo.smartfactory.entity.series.SeriesManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Setter
public class Factory {
    public static final Logger LOGGER = LogManager.getLogger("Factory");

    /**
     * Factory name
     */
    private String name;

    /**
     * Tick length
     */
    private long tickLengthMillis;

    /**
     * Foundation date
     */
    private final Instant foundationDate;

    /**
     * All reports
     */
    private List<Report> reports;

    /**
     * Current tick
     */
    private long currentTick = 0;

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
    private final SeriesManager seriesManager;

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
    private List<TickObserver> behavioralsList = new ArrayList<>();

    public Factory(String name, int tickLengthMillis, Instant foundationDate) {
        this.name = name;
        this.foundationDate = foundationDate;
        this.tickLengthMillis = tickLengthMillis;
        this.eventManager = new EventManager(this);
        this.seriesManager = new SeriesManager(this);
        this.eventFacade = new EventFacade(this.eventManager);
    }

    /**
     * Returns instant of simulation time
     */
    public Instant now() {
        return foundationDate.plusMillis(currentTick * tickLengthMillis);
    }

    /**
     * Simulate one iteration
     */
    public synchronized void simulate() {
        currentTick++;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LOGGER.info("Tact: " + currentTick + " at " + LocalDateTime.ofInstant(now(), ZoneId.systemDefault()).format(formatter));
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

    public Director getFirstAvailableDirector() {
        return people.stream()
                .filter(person -> person instanceof Director)
                .filter(person -> person.getState().isAvailable())
                .map(person -> (Director) person)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No available Director found"));
    }

    public Inspector getFirstAvailableInspector() {
        return people.stream()
                .filter(person -> person instanceof Inspector)
                .filter(person -> person.getState().isAvailable())
                .map(person -> (Inspector) person)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No available Inspector found"));
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
                + ", currentTact=" + currentTick + "}";
    }
}
