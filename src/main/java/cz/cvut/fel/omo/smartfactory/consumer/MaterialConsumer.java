package cz.cvut.fel.omo.smartfactory.consumer;

import cz.cvut.fel.omo.smartfactory.Material;

/**
 * Consumer for Materials
 */
public class MaterialConsumer extends AbstractConsumer<Material> {
    /**
     * Create consumer
     *
     * @param initialValue The initial value
     */
    public MaterialConsumer(Material initialValue) {
        super(initialValue);
    }

    @Override
    public void accept(Material material) {
        consumed.consume(material);
    }

    @Override
    public float calculateExpenses(float pricePerUnit) {
        return consumed.getCount() * pricePerUnit;
    }
}
