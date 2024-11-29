package cz.cvut.fel.omo.smartfactory.entity.event;

public interface FactoryEventListener {

    /**
     * Listener callback
     */
    void onEvent(FactoryEvent event);
}
