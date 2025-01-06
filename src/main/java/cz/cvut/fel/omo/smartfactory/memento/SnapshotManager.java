package cz.cvut.fel.omo.smartfactory.memento;

import java.util.ArrayList;
import java.util.List;

public class SnapshotManager {
    List<Snapshot> snapshots = new ArrayList<>();

    /**
     * Add snapshot to snapshots
     *
     * @param snapshot The snapshot to add
     */
    public void add(Snapshot snapshot) {
        snapshots.add(snapshot);
    }

    /**
     * Get snapshot at index
     *
     * @param index The index of snapshot
     * @return the snapshot
     */
    public Snapshot get(int index) {
        return snapshots.get(index);
    }

    /**
     * Get all snapshots
     *
     * @return Returns all snapshots
     */
    public List<Snapshot> getAll() {
        return snapshots;
    }
}
