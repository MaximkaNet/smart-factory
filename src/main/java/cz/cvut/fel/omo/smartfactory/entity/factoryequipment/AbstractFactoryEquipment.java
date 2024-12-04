package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Behavioral;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.ElectricityConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.MaterialConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.OilConsumer;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.FactoryEquipmentState;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.ReadyState;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract factory equipment
 */
@Getter
@Setter
public abstract class AbstractFactoryEquipment implements ProductionUnit, Behavioral {
    /**
     * The entity id
     */
    protected final String id;

    /**
     * Price per usage
     */
    protected final float pricePerUsage;

    /**
     * Maximum health
     */
    protected final float maximumHealth;

    /**
     * Current product
     */
    protected Product subject = null;

    /**
     * Product state
     */
    protected boolean finished = false;

    /**
     * Electricity consumer
     */
    protected ElectricityConsumer electricityConsumer;

    /**
     * Oil consumer
     */
    protected OilConsumer oilConsumer;

    /**
     * Material consumer
     */
    protected MaterialConsumer materialConsumer;

    /**
     * Number of usage (or just processed products)
     */
    protected float usageTime = 0.0f;

    /**
     * Next production unit on the line
     */
    protected ProductionUnit next = null;

    /**
     * Actual health
     */
    protected float actualHealth;

    /**
     * Factory reference
     */
    protected Factory factory;
    protected ProductionLine productionLine;

    /**
     * Manufacturing entity state
     */
    protected FactoryEquipmentState state = new ReadyState(this);

    protected AbstractFactoryEquipment(String id, float pricePerUsage, float maximumHealth) {
        this.id = id;
        this.pricePerUsage = pricePerUsage;
        this.actualHealth = maximumHealth;
        this.maximumHealth = maximumHealth;
    }

    public boolean accept(Product product) {
        if (product == null || subject != null) {
            return false;
        }
        subject = product;
        return true;
    }

    public abstract void process(long deltaTime);

    public void update(long deltaTime) {
        state.process(deltaTime);
    }

    public Product pop() {
        Product product = subject;
        subject = null;
        return product;
    }

    public Product processNext(Product product) {
        if (next == null) {
            return product;
        }
        state.accept(product);
        return processNext(state.pop());
    }
}
