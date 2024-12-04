package cz.cvut.fel.omo.smartfactory.state.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import cz.cvut.fel.omo.smartfactory.state.ProductionUnitState;

public abstract class FactoryEquipmentState implements ProductionUnitState {
    protected final AbstractFactoryEquipment context;

    public FactoryEquipmentState(AbstractFactoryEquipment context) {
        this.context = context;
    }

    public abstract void process(long deltaTime);

    public abstract boolean accept(Product product);

    public abstract Product pop();
}
