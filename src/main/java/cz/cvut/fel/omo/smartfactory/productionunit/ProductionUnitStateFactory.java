package cz.cvut.fel.omo.smartfactory.entity.productionunit;

public interface ProductionUnitStateFactory {

    /**
     * Create ready state
     *
     * @param context The production unit
     */
    ProductionUnitState createReadyState(ProductionUnit context);

    /**
     * Create processing state
     *
     * @param context The production state
     */
    ProductionUnitState createProcessingState(ProductionUnit context);

    /**
     * Create finished state
     *
     * @param context The production unit
     */
    ProductionUnitState createFinishedState(ProductionUnit context);
}
