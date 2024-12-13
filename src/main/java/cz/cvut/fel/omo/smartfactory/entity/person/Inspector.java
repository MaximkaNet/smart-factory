package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryUsageIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;

public class Inspector extends Person implements FactoryVisitor {
    private FactoryUsageIterator iterator;

    public Inspector(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void startVisit(FactoryUsageIterator iterator) {
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
            System.out.println(this + " changed state to ready as the iteration is complete");
            state.ready();
        }
    }

    @Override
    public void visit(Person person) {
        System.out.println(this + " is inspecting Person: " + person);
    }

    @Override
    public void visit(Machine machine) {
        System.out.println(this + " is inspecting Machine: " + machine);
    }

    @Override
    public void visit(Robot robot) {
        System.out.println(this + " is inspecting Robot: " + robot);
    }

    @Override
    public void visit(Product product) {
        System.out.println(this + " is inspecting Product: " + product);
    }
}
