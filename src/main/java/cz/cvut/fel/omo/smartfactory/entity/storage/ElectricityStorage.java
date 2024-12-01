package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.ElectricityConsumer;
import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;

public final class ElectricityStorage extends AbstractStorage<Float> {
    public ElectricityStorage(float price) {
        super(price);
    }

    @Override
    public void accept(Float aFloat) {
        storage += aFloat;
    }

    @Override
    public FactoryConsumer<Float> createConsumer() {
        return new ElectricityConsumer(this);
    }
}
