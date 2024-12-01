package cz.cvut.fel.omo.smartfactory.entity.storage.consumer;

import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;
import lombok.Getter;

@Getter
public abstract class AbstractConsumer<T> implements FactoryConsumer<T> {

    protected T consumed;

    protected final Storage<T> parentStorage;

    protected AbstractConsumer(Storage<T> storage) {
        this.parentStorage = storage;
    }

    public abstract void accept(T t);

    @Override
    public Storage<T> getStorage() {
        return parentStorage;
    }
}
