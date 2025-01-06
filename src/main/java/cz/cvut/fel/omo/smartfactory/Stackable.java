package cz.cvut.fel.omo.smartfactory;

public interface Stackable<T> {
    /**
     * Consume the stackable
     *
     * @param stackable stackable object
     */
    void consume(T stackable);

    T pop(long count);
}
