package cz.cvut.fel.omo.smartfactory.entity.storage.consumer;

import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;

/**
 * Electricity consumer
 */
public final class ElectricityConsumer extends AbstractConsumer<Float> {

    public ElectricityConsumer(Storage<Float> storage) {
        super(storage);
    }

    @Override
    public void accept(Float aFloat) {
        parentStorage.accept(aFloat);
        consumed += aFloat;
    }
}
