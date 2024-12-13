package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.event.FactoryEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.ElectricityConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.MaterialConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.OilConsumer;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.FactoryEquipmentState;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.ReadyState;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Logger;

/**
 * Abstract factory equipment
 */
@Getter
@Setter
public abstract class AbstractFactoryEquipment implements ProductionUnit {

    protected final Logger LOGGER = Logger.getLogger("Factory equipment");

    /**
     * The entity id
     */
    protected final String discriminator;

    /**
     * string name
     */
    protected final String name;

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

    /**
     * Manufacturing entity state
     */
    protected FactoryEquipmentState state = new ReadyState(this);

    protected AbstractFactoryEquipment(String id, String name, float pricePerUsage, float maximumHealth) {
        this.discriminator = id;
        this.name = name;
        this.pricePerUsage = pricePerUsage;
        this.actualHealth = maximumHealth;
        this.maximumHealth = maximumHealth;
    }

    @Override
    public boolean accept(Product product) {
        if (product == null || subject != null) {
            return false;
        }
        subject = product;
        return true;
    }

    @Override
    public abstract void process(long deltaTime);

    @Override
    public Product pop() {
        Product product = subject;
        subject = null;
        return product;
    }

    @Override
    public void reset() {
        ProductionUnit next = this.next;
        this.next = null;

        // If chain has next reset it
        if (next != null) {
            next.reset();
        }
    }

    @Override
    public boolean isAvailable() {
        return next == null;
    }

    @Override
    public Product processNext(Product product, long dt) {
        if (next == null) {
            return product;
        }
        state.accept(product);
        state.process(dt);
        return processNext(state.pop(), dt);
    }

    /**
     * Generate outage event using event manager from factory
     */
    public void generateOutageEvent() {
        FactoryEvent event = new OutageEvent(123, this, factory.now());
        factory.getEventManager().notifyListeners(event);
    }
}
