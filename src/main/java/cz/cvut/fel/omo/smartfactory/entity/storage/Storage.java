package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;

public interface Storage<T> {

    float getPrice();

    void accept(T t);

    /**
     * Create consumer for storage
     */
    FactoryConsumer<T> createConsumer();

}
