package cz.cvut.fel.omo.smartfactory.visitor;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.factory.iterator.FactoryIterator;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The visitor
 */
public class Visitor implements FactoryVisitor {

    private static final Logger LOGGER = LogManager.getLogger("Visitor");

    /**
     * The visitor name
     */
    private final String name;

    /**
     * Create visitor
     *
     * @param name The visitor name
     */
    public Visitor(String name) {
        this.name = name;
    }

    /**
     * Start the process of visiting the factory
     *
     * @param iterator The factory iterator
     */
    public void visitFactory(FactoryIterator iterator) {
        while (iterator.hasNext()) {
            Visitable visitableEntity = iterator.next();
            visitableEntity.acceptVisitor(this);
        }
    }

    @Override
    public void visitWorker(Worker worker) {
        LOGGER.info("{} is checking Worker: {}", name, worker.getId().toString());
    }

    @Override
    public void visitMachine(Machine machine) {
        LOGGER.info("{} is checking Machine: {}", name, machine.getId().toString());
    }

    @Override
    public void visitRobot(Robot robot) {
        LOGGER.info("{} is checking Robot: {}", name, robot.getId().toString());
    }
}
