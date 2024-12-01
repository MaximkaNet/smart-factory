package cz.cvut.fel.omo.smartfactory.entity.factoryequipment;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.ElectricityConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.MaterialConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.OilConsumer;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.FactoryEquipmentState;
import cz.cvut.fel.omo.smartfactory.state.factoryequipment.ReadyState;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public abstract class AbstractFactoryEquipment implements ProductionUnit {
    /**
     * The entity id
     */
    private final String id;

    private Product subject = null;

    protected boolean finished = false;

    private ElectricityConsumer electricityConsumer;
    private OilConsumer oilConsumer;
    private MaterialConsumer materialConsumer;

    private final float pricePerUsage;

    /**
     * Number of usage (or just processed products)
     */
    private int numberOfUsage = 0;

    /**
     * Next production unit on the line
     */
    private ProductionUnit next = null;

    private final float healthy;
    private float health;

    protected Factory factory;
    protected ProductionLine productionLine;

    /**
     * Manufacturing entity state
     */
    private FactoryEquipmentState state = new ReadyState(this);

    protected AbstractFactoryEquipment(String id, float pricePerUsage, float healthy) {
        this.id = id;
        this.pricePerUsage = pricePerUsage;
        this.healthy = health;
        this.health = healthy;
    }

    public void setNext(ProductionUnit unit) {
        next = Objects.requireNonNull(unit);
    }

    public boolean accept(Product product) {
        if (product == null || subject != null) {
            return false;
        }
        subject = product;
        return true;
    }

    public abstract void process();

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
        state.process();
        return processNext(state.pop());
    }
}
