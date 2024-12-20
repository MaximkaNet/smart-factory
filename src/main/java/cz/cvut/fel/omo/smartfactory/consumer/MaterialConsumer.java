package cz.cvut.fel.omo.smartfactory.consumer;

import cz.cvut.fel.omo.smartfactory.Material;

/**
 * Consumer for Materials
 */
public class MaterialConsumer extends AbstractConsumer<Material> {
    @Override
    public void accept(Material material) {
        consumed.consume(material);
    }

    @Override
    public float calculateExpenses(float pricePerUnit) {
        return consumed.getCount() * pricePerUnit;
    }
}
