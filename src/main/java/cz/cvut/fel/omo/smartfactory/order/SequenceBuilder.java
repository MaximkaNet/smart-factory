package cz.cvut.fel.omo.smartfactory.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Sequence builder
 */
public class SequenceBuilder {

    List<String> sequence = new ArrayList<>();

    /**
     * Add simple worker
     */
    public SequenceBuilder addWorker() {
        sequence.add("W");
        return this;
    }

    /**
     * Add simple robot
     */
    public SequenceBuilder addRobot() {
        sequence.add("R");
        return this;
    }

    /**
     * Add simple machine
     */
    public SequenceBuilder addMachine() {
        sequence.add("M");
        return this;
    }

    /**
     * Build sequence
     */
    public List<String> build() {
        return sequence;
    }
}
