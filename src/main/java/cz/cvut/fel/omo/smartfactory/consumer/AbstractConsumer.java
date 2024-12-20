package cz.cvut.fel.omo.smartfactory.consumer;

import lombok.Getter;

/**
 * Abstract consumer
 *
 * @param <T>
 */
@Getter
public abstract class AbstractConsumer<T> {

    /**
     * Consumed units
     */
    protected T consumed;

    protected AbstractConsumer() {
    }

    /**
     * Accept object for consumption handling
     *
     * @param t The object for consumption
     */
    public abstract void accept(T t);

    /**
     * Calculate total expenses
     *
     * @param pricePerUnit The price per unit (kw/h, lites/h, ...)
     * @return Total expenses
     */
    public abstract float calculateExpenses(float pricePerUnit);
}
