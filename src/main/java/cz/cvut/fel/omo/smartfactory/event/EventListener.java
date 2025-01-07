package cz.cvut.fel.omo.smartfactory.event;

/**
 * The EventListener interface
 */
public interface EventListener {

    /**
     * The listener callback that apply the AbstractEvent
     *
     * @param event The event for processing
     */
    void receiveEvent(AbstractEvent event);
}
