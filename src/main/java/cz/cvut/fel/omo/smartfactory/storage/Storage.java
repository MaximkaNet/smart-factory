package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;

public interface Storage<T> {

    @Deprecated
    float getPrice();

    void push(T t);

    T pop(long count);

    /**
     * Create consumer for storage
     */
    FactoryConsumer<T> createConsumer();

}
