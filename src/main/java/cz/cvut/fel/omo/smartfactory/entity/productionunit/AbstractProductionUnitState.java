package cz.cvut.fel.omo.smartfactory.entity.productionunit;

/**
 * Abstract production unit state
 */
public abstract class AbstractProductionUnitState implements ProductionUnitState {

    /**
     * The context
     */
    protected final ProductionUnit context;

    /**
     * State factory
     */
    protected final ProductionUnitStateFactory stateFactory;

    /**
     * Create production unit state
     *
     * @param context      The context
     * @param stateFactory The state factory
     */
    public AbstractProductionUnitState(ProductionUnit context, ProductionUnitStateFactory stateFactory) {
        this.context = context;
        this.stateFactory = stateFactory;
    }
}
