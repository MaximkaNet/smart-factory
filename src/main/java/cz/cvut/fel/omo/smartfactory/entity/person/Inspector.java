package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;

public class Inspector extends Person implements FactoryVisitor {
    public Inspector(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public void update(long deltaTime) {
        // TODO: implement the update method
    }

    @Override
    public void visit(Person person) {
        System.out.println(this + " is visiting Person: " + person);
    }

    @Override
    public void visit(Machine machine) {
        System.out.println(this + " is visiting Machine: " + machine);
    }

    @Override
    public void visit(Robot robot) {
        System.out.println(this + " is visiting Robot: " + robot);
    }

    @Override
    public void visit(Product product) {
        System.out.println(this + " is visiting Product: " + product);
    }
}
