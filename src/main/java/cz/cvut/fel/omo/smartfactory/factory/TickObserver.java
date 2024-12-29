package cz.cvut.fel.omo.smartfactory.factory;

/**
 * Tick observer
 */
public interface TickObserver {

    /**
     * Update function
     *
     * @param deltaTime The delta time
     */
    void update(long deltaTime);
}
