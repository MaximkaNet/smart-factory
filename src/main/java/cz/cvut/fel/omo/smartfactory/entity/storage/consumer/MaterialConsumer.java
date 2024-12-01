package cz.cvut.fel.omo.smartfactory.entity.storage.consumer;

import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;

/**
 * Material consumer
 */
public final class MaterialConsumer extends AbstractConsumer<Integer> {
    public MaterialConsumer(Storage<Integer> storage) {
        super(storage);
    }

    @Override
    public void accept(Integer integer) {
        parentStorage.accept(integer);
        consumed += integer;
    }
}
