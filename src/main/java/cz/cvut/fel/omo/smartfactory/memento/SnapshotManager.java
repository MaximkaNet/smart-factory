package cz.cvut.fel.omo.smartfactory.memento;

import java.util.ArrayList;
import java.util.List;

public class SnapshotManager {
    List<Snapshot> snapshots = new ArrayList<>();

    public void add(Snapshot snapshot) {
        snapshots.add(snapshot);
    }

    public Snapshot get(int index) {
        return snapshots.get(index);
    }

    public List<Snapshot> getAll() {
        return snapshots;
    }
}
