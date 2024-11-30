package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Robot;
import cz.cvut.fel.omo.smartfactory.entity.event.EventManager;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
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
    private String name;
    private List<Report> reports;
    private Integer tactLengthMilliseconds = 500;
    private Integer currentTact = 0;
    private boolean isRunning = false;
    private List<Person> people;
    private List<ProductionLine> productionLines;
    private List<Robot> robots;
    private List<Machine> machines;
    private RepairmanPool repairmanPool;
    private List<TactSubscriber> tactSubscribers = new ArrayList<>();
    private EventManager eventFacade;

    public Factory(String name, int tactLengthMilliseconds, List<Person> people, List<Machine> machines, List<Robot> robots, List<ProductionLine> productionLines) {
        this.name = name;
        this.tactLengthMilliseconds = tactLengthMilliseconds;
        this.productionLines = productionLines;
        this.robots = robots;
        this.machines = machines;
        this.people = people;
        eventFacade = new EventManager(this);
    }

    /**
     * Main simulation method
     */
    public void simulate() {
        currentTact++;
        System.out.println("tact: " + currentTact);
        // run everything that needs to be run on one tact
        // send message about new tact for each tact subscriber
        tactSubscribers.forEach(subscriber -> subscriber.onNewTact(currentTact));
        // execute next tact in repairmanPool
        repairmanPool.executeRepairs();
        // production lines
        for (ProductionLine productionLine : productionLines) {
            productionLine.process();
        }

        // I have added sleeping here
        // will be removed and is here to observe factory working
        try {
            Thread.sleep(tactLengthMilliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void simulate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            simulate();
        }
    }

    /**
     * Create outage report in a time span
     *
     * @return stringify report
     */
    public OutagesReport createOutageReport(ZonedDateTime from, ZonedDateTime to) {
        return new OutagesReport(from, to, this);
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
                + ", isRunning=" + isRunning
                + ", tactLengthMilliseconds=" + tactLengthMilliseconds
                + ", currentTact=" + currentTact + "}";
    }
}
