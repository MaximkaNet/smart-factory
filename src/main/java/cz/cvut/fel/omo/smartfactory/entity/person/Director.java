package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Director extends Person implements FactoryVisitor {
    public static final Logger LOGGER = LogManager.getLogger("Director");

    private FactoryTreeIterator iterator;

    public Director(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void startVisit(FactoryTreeIterator iterator) {
        if (state.isAvailable()) {
            state.work();
        } else {
            throw new IllegalStateException("Cannot start visiting as " + this + " is not available");
        }

        this.iterator = iterator;
    }

    @Override
    public void update(long deltaTime) {
        if (!state.isExecuting()) {
            return;
        }

        iterator.getCurrent().acceptVisitor(this);

        if (iterator.hasNext()) {
            iterator.next();
        } else {
            LOGGER.info("{} changed state to ready as the iteration is complete", this);
            state.ready();
        }
    }

    @Override
    public void visit(Person person) {
        LOGGER.info("{} is checking Person: {}", this, person);
    }

    @Override
    public void visit(Machine machine) {
        LOGGER.info("{} is checking Machine: {}", this, machine);
    }

    @Override
    public void visit(Robot robot) {
        LOGGER.info("{} is checking Robot: {}", this, robot);
    }

    @Override
    public void visit(Product product) {
        LOGGER.info("{} is checking Product: {}", this, product);
    }
}
