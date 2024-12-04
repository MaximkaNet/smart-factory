package cz.cvut.fel.omo.smartfactory.entity.person;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;

public class Worker extends Person implements ProductionUnit {

    private ProductionUnit next;
    private float cost;

    public Worker(String discriminator, String firstName, String lastName, float cost) {
        super(discriminator, firstName, lastName);
        this.cost = cost;
    }

    @Override
    public String getDiscriminator() {
        return "";
    }

    @Override
    public void process(long dt) {

    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }

    @Override
    public void setNext(ProductionUnit unit) {
        this.next = unit;
    }

    @Override
    public ProductionUnit getNext() {
        return next;
    }

    @Override
    public Product processNext(Product product) {
        return null;
    }

    @Override
    public void update(long deltaTime) {

    }

    @Override
    public void accept(FactoryVisitor visitor) {
        visitor.visit(this);
    }
}
