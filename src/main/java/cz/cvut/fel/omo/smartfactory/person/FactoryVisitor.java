package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;

public interface FactoryVisitor {
    void visit(Person person);

//    void visit(Machine machine);
//
//    void visit(Robot robot);

    void visit(Product product);
}
