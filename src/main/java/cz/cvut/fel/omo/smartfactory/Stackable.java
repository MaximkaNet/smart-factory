package cz.cvut.fel.omo.smartfactory;

public interface Stackable<T> {
    void consume(T stackable);

    T pop(long count);
}
