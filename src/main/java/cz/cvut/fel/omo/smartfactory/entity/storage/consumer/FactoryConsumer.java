package cz.cvut.fel.omo.smartfactory.entity.storage.consumer;

import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;

public interface FactoryConsumer<T> {

    /**
     * Get parent storage
     */
    Storage<T> getStorage();

    void accept(T t);
}
