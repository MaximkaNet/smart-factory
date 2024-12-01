package cz.cvut.fel.omo.smartfactory.entity.storage.consumer;

import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;

/**
 * Oil consumer
 */
public final class OilConsumer extends AbstractConsumer<Float> {
    public OilConsumer(Storage<Float> storage) {
        super(storage);
    }

    @Override
    public void accept(Float aFloat) {
        parentStorage.accept(aFloat);
        consumed += aFloat;
    }
}
