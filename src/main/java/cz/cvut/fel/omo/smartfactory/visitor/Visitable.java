package cz.cvut.fel.omo.smartfactory.visitor;

/**
 * The visitable interface
 */
public interface Visitable {
    /**
     * Accept the visitor
     *
     * @param visitor Object to be accepted in visit
     */
    void acceptVisitor(FactoryVisitor visitor);
}
