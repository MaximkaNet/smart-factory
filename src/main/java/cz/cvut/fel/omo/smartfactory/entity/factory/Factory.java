package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.AbstractManufacturingEntity;
import cz.cvut.fel.omo.smartfactory.entity.Machine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Robot;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEventManager;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Factory {
    private final String name;
    private final List<Report> reports = new ArrayList<>();
    private final int tickMs;

    private final List<ProductionLine> lines;
    private final List<Robot> robots;
    private final List<Machine> machines;
    private final List<Person> people;
    private RepairmanPool repairmanPool;

    /**
     * Event manager
     */
    private FactoryEventManager eventManager = new FactoryEventManager();


    public Factory(String name,
                   int tickMs,
                   List<Person> people,
                   List<Machine> machines,
                   List<Robot> robots,
                   List<ProductionLine> productionLines
    ) {
        this.name = name;
        this.tickMs = tickMs;
        this.lines = productionLines;
        this.robots = robots;
        this.machines = machines;
        this.people = people;
    }

    /**
     * Factory builder
     *
     * @return Factory builder
     */
    public static FactoryBuilder builder() {
        return new FactoryBuilder();
    }

    /**
     * Main simulation method
     */
    public void simulate() {
        //
        for (ProductionLine productionLine : lines) {
            productionLine.process();
        }
    }

    public void simulate(int ticks) {
        for (int i = 0; i < ticks; i++) {
            simulate();
        }
    }

    /**
     * Create outage report
     *
     * @return stringify report
     */
    public String createOutageReport() {
        return "";
    }

    /**
     * This method will be used in memento pattern
     *
     * @param date restore date
     */
    public void restore(String date) {

    }

    /**
     * Conduct an inspection of the factory
     */
    public void inspect() {

    }

    /**
     * Get manufacturing entity (machine or robot) by id
     */
    public AbstractManufacturingEntity getManufacturingEntity(String id) {
        // check id prefix
        // find entity
        // return manufacturing entity
        return null;
    }

    /**
     * Generate event
     *
     * @param event Factory event
     */
    public void generateEvent(FactoryEvent event) {
        eventManager.notifyListeners(event);
    }

}
