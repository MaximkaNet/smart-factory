package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Factory builder
 */
public class FactoryBuilder {

    /**
     * The factory name
     */
    private String name = "Default Factory";

    /**
     * Factory repairman pool
     */
    private RepairmenPool repairmanPool = new RepairmenPool(new ArrayList<>());

    /**
     * The production unit manager
     */
    private ProductionUnitManager productionUnitManager = new ProductionUnitManager();

    /**
     * Create factory builder
     */
    public FactoryBuilder() {
    }

    /**
     * Set factory name
     *
     * @param name The factory name
     * @return Current builder instance
     */
    public FactoryBuilder setName(String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    /**
     * Set production unit manager
     *
     * @param manager The production unit manager
     * @return Current builder instance
     */
    public FactoryBuilder setProductionUnitManager(ProductionUnitManager manager) {
        this.productionUnitManager = Objects.requireNonNull(manager);
        return this;
    }

    /**
     * Set repairman pool
     *
     * @param pool The repairman pool
     * @return Current builder instance
     */
    public FactoryBuilder setRepairmenPool(RepairmenPool pool) {
        this.repairmanPool = Objects.requireNonNull(pool);
        return this;
    }

    /**
     * Build the factory
     *
     * @return Built factory
     */
    public Factory build() {
        Factory factory = new Factory(name);
        factory.setRepairmanPool(repairmanPool);
        factory.setProductionUnitManager(productionUnitManager);
        return factory;
    }
}
