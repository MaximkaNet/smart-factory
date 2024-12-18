package cz.cvut.fel.omo.smartfactory.entity.equipment.state.factory;

import cz.cvut.fel.omo.smartfactory.entity.equipment.state.BrokenState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnitStateFactory;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.FinishedState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ProcessingState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.state.ReadyState;

/**
 * Equipment state factory
 */
public class EquipmentStateFactory implements ProductionUnitStateFactory {
    @Override
    public ProductionUnitState createReadyState(ProductionUnit context) {
        return new ReadyState(context, this);
    }

    @Override
    public ProductionUnitState createProcessingState(ProductionUnit context) {
        return new ProcessingState(context, this);
    }

    @Override
    public ProductionUnitState createFinishedState(ProductionUnit context) {
        return new FinishedState(context, this);
    }

    public ProductionUnitState createBrokenState(ProductionUnit context) {
        return new BrokenState(context, this);
    }
}
