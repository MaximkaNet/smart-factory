package cz.cvut.fel.omo.smartfactory.state.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;

public class BrokenState extends FactoryEquipmentState {

    public BrokenState(AbstractFactoryEquipment context) {
        super(context);
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
}
