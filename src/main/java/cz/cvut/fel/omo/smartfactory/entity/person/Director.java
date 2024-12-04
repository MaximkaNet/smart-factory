package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryTreeIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;

public class Director extends Person implements FactoryVisitor {
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

        iterator.getCurrent().accept(this);

        if (iterator.hasNext()) {
            iterator.next();
        }
    }

    @Override
    public void visit(Person person) {
        System.out.println(this + " is checking Person: " + person);
    }

    @Override
    public void visit(Machine machine) {
        System.out.println(this + " is checking Machine: " + machine);
    }

    @Override
    public void visit(Robot robot) {
        System.out.println(this + " is checking Robot: " + robot);
    }

    @Override
    public void visit(Product product) {
        System.out.println(this + " is checking Product: " + product);
    }
}
