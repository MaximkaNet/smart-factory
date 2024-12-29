package cz.cvut.fel.omo.smartfactory.memento;

import java.time.Instant;

/**
 * The object snapshot
 */
public abstract class Snapshot {
    /**
     * Created time
     */
    Instant createdAt;

    /**
     * Export snapshot to JSON
     *
     * @param pretty Pretty flag
     */
    public abstract String toJson(boolean pretty);

    /**
     * Export snapshot to JSON
     */
    public String toJson() {
        return toJson(false);
    }
}
