package cz.cvut.fel.omo.smartfactory.storage.consumer;

import lombok.Getter;

@Getter
public abstract class AbstractConsumer<T> {

    protected T consumed;

    protected AbstractConsumer() {

    }

    public abstract void accept(T t);
}
