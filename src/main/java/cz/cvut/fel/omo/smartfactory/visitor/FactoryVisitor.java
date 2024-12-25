package cz.cvut.fel.omo.smartfactory.visitor;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.worker.Worker;

/**
 * Factory visitor
 */
public interface FactoryVisitor {
    /**
     * Visit worker
     *
     * @param worker The worker
     */
    void visitWorker(Worker worker);

    /**
     * Visit machine
     *
     * @param machine The machine
     */
    void visitMachine(Machine machine);

    /**
     * Visit robot
     *
     * @param robot The robot
     */
    void visitRobot(Robot robot);
}
