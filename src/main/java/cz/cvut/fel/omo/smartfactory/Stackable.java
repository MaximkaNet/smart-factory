package cz.cvut.fel.omo.smartfactory;

/**
 * The stackable interface
 *
 * @param <T> The generic type
 */
public interface Stackable<T> {
    /**
     * Consume the stackable
     *
     * @param stackable stackable object
     */
    void consume(T stackable);

    /**
     * Get new instance of stackable object and remove count of items from original object.
     * If count more than actual number of products returns instance of original stackable object
     *
     * @param count Product amount
     * @return New product instance or original stackable object is stackable has no more products
     */
    T pop(long count);
}
