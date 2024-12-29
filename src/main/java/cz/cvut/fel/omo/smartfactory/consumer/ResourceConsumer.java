package cz.cvut.fel.omo.smartfactory.consumer;

/**
 * The resource consumer for electricity (kw/h), oil(liters/h) or another type of resource
 */
public class ResourceConsumer extends AbstractConsumer<Float> {

    /**
     * Create resource consumer
     */
    public ResourceConsumer() {
        super(0.0f);
    }

    @Override
    public void accept(Float aFloat) {
        consumed += aFloat;
    }

    @Override
    public float calculateExpenses(float pricePerUnit) {
        return consumed * pricePerUnit;
    }
}
