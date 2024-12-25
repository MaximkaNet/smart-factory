package cz.cvut.fel.omo.smartfactory.visitor;

public interface Visitable {
    void acceptVisitor(FactoryVisitor visitor);
}
