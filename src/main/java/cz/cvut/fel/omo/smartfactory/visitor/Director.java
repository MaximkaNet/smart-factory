package cz.cvut.fel.omo.smartfactory.person;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The factory director. He moves through each
 * production line and visits each ProductionUnit
 */
public class Director implements FactoryVisitor {
    public static final Logger LOGGER = LogManager.getLogger("Director");

    private FactoryTreeIterator iterator;

    public Director() {
    }

    public void runVisiting(FactoryTreeIterator iterator) {
        while (iterator.hasNext()) {
            AbstractProductionUnit productionUnit = iterator.next();
            productionUnit.acceptVisitor(this);
        }
    }

    @Override
    public void visit(Worker worker) {
        LOGGER.info("{} is checking Person: {}", this, worker);
    }

    @Override
    public void visit(Machine machine) {
        LOGGER.info("{} is checking Machine: {}", this, machine);
    }

    @Override
    public void visit(Robot robot) {
        LOGGER.info("{} is checking Robot: {}", this, robot);
    }
}
