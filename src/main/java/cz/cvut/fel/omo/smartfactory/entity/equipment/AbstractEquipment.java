package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Material;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract factory equipment
 */
@Getter
@Setter
public abstract class AbstractFactoryEquipment extends AbstractProductionUnit {

    /**
     * Maximum equipment health
     */
    private final float maximumHealth;

    /**
     * Actual health
     */
    private float actualHealth = 0;

    /**
     * Material storage
     */
    private Storage<Material> materialStorage = null;


//    /**
//     * Price per usage
//     */
//    protected final float pricePerUsage;
//
//    /**
//     * Electricity consumer
//     */
//    protected ElectricityConsumer electricityConsumer;
//
//    /**
//     * Oil consumer
//     */
//    protected OilConsumer oilConsumer;
//
//    /**
//     * Material consumer
//     */
//    protected MaterialConsumer materialConsumer;
//
//    /**
//     * Number of usage (or just processed products)
//     */
//    protected float usageTime = 0.0f;

    /**
     * Create factory equipment
     *
     * @param name   The equipment name
     * @param health The maximum health
     */
    public AbstractFactoryEquipment(String name, float health) {
        super(name);
        this.maximumHealth = health;
    }

    public boolean isBroken() {
        return false;
    }

//    @Override
//    public Product processNext(Product product, long dt) {
//        if (next == null) {
//            return product;
//        }
//        state.accept(product);
//        state.process(dt);
//        return processNext(state.pop(), dt);
//    }

//    /**
//     * Generate outage event using event manager from factory
//     */
//    public void generateOutageEvent() {
//        FactoryEvent event = new OutageEvent(123, this, factory.now());
//        factory.getEventManager().notifyListeners(event);
//    }
}
