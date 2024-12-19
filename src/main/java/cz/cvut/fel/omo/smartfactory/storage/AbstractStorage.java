package cz.cvut.fel.omo.smartfactory.entity.storage;

import cz.cvut.fel.omo.smartfactory.entity.storage.consumer.FactoryConsumer;
import lombok.Getter;

public abstract class AbstractStorage<T> implements Storage<T> {
    @Getter
    protected final float price;
    protected T storage;

    public AbstractStorage(float price) {
        this.price = price;
    }

    public abstract void push(T t);

    public abstract FactoryConsumer<T> createConsumer();
}
