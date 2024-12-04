package cz.cvut.fel.omo.smartfactory.state.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;

public class FinishedState extends FactoryEquipmentState {

    public FinishedState(AbstractFactoryEquipment context) {
        super(context);
    }

    public void process(long dt) {

    }

    @Override
    public boolean accept(Product product) {
        return false;
    }

    @Override
    public Product pop() {
        Product product = context.pop();
        context.setState(new ReadyState(context));
        return product;
    }
}
