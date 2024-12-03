package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Robot;

public interface FactoryVisitor {
    void visit(Person person);

    void visit(Machine machine);

    void visit(Robot robot);

    void visit(Product product);
}
