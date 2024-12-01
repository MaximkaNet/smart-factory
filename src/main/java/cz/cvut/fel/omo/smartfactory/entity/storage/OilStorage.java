package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.OilConsumer;

public final class OilStorage extends AbstractStorage<Float> {

    public OilStorage(float price) {
        super(price);
    }

    @Override
    public void accept(Float aFloat) {
        storage += aFloat;
    }

    @Override
    public FactoryConsumer<Float> createConsumer() {
        return new OilConsumer(this);
    }
}
