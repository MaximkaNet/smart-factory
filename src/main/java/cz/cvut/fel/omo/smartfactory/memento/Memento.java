package cz.cvut.fel.omo.smartfactory.memento;

public interface Memento {
    /**
     * Create a snapshot of the object
     */
    Snapshot save();

    /**
     * Restore object from snapshot
     *
     * @param snapshot The object snapshot
     */
    void restore(Snapshot snapshot);
}
