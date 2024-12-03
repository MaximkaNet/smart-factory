package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryIterator.FactoryPriorityIterator;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;

public class Inspector extends Person implements FactoryVisitor {
    private FactoryPriorityIterator iterator;

    public Inspector(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public void startVisit(FactoryPriorityIterator iterator) {
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

        // TODO: ProductionUnit has to implement the accept method for the visitor
//        iterator.getCurrent().accept(this);

        if (iterator.hasNext()) {
            iterator.next();
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
