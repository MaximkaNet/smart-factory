package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;

public final class MaterialStorage extends AbstractStorage<Integer> {

    public MaterialStorage(float price) {
        super(price);
    }

    @Override
    public void accept(Integer integer) {
        storage += integer;
    }

    @Override
    public FactoryConsumer<Integer> createConsumer() {
        return null;
    }
}
