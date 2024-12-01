package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
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

    /**
     * Consumption
     */
    private final Consumption consumption;

    /**
     * Prices for consumption and usage
     */
    private final Price prices;

    /**
     * Number of usage (or just processed products)
     */
    private int numberOfUsage = 0;

    /**
     * Next production unit on the line
     */
    private ProductionUnit next = null;

    private final float healthy = 0;
    private float health = 0;

    protected Factory factory;
    protected ProductionLine productionLine;

    /**
     * Manufacturing entity state
     */
    private FactoryEquipmentState state = new ReadyState(this);

    protected AbstractFactoryEquipment(String id, Consumption consumption, Price prices) {
        this.factory = productionLine.getFactory();
        this.id = id;
        this.consumption = consumption;
        this.prices = prices;
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

    public float getTotalElectricityConsumption() {
        return 0;
    }

    public float getTotalOilConsumption() {
        return 0;
    }

    public int getTotalMaterialConsumption() {
        return 0;
    }

    public float getTotalElectricityPrice() {
        return 0;
    }

    public float getTotalOilPrice() {
        return 0;
    }

    public float getTotalMaterialPrice() {
        return 0;
    }

    public float getTotalUsagePrice() {
        return 0;
    }

    //    @Override
//    public void receiveEvent(Event event) {
//        System.out.println("received: " + event);
//    }

    /**
     * Consumption per tick
     */
    @Getter
    protected static class Consumption {
        private final float electricity;
        private final float oil;
        private final int material;

        private float electricityConsumption = 0;
        private float oilConsumption = 0;
        private float materialConsumption = 0;

        public Consumption(float electricity, float oil, int material) {
            this.electricity = electricity;
            this.oil = oil;
            this.material = material;
        }

        public void updateElectricity(float fine) {
            electricityConsumption += electricity + fine;
        }

        public void updateOil(float fine) {
            oilConsumption += oil + fine;
        }

        public void updateMaterial() {
            materialConsumption += material;
        }
    }

    /**
     * Prices per tick
     */
    protected static class Price {
        protected final float electricity;
        protected final float oil;
        protected final float perUsage;

        public Price(float electricity, float oil, float perUsage) {
            this.electricity = electricity;
            this.oil = oil;
            this.perUsage = perUsage;
        }
    }
}
