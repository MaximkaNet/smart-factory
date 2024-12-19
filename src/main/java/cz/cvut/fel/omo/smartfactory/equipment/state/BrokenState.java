package cz.cvut.fel.omo.smartfactory.entity.equipment.state;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ProcessingState;

public class BrokenState extends AbstractEquipmentState {

    public BrokenState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        super(context, stateFactory);
    }

    @Override
    public void process(long dt) {

    }

    @Override
    public boolean repair(float power) {

        AbstractEquipment equipment = (AbstractEquipment) context;

        if (equipment.repair(power)) {
            equipment.setState(new ProcessingState(equipment, stateFactory));
            return true;
        }
        return false;
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
    public Product peek() {
        return null;
    }
}
