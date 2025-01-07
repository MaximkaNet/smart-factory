package cz.cvut.fel.omo.smartfactory.order;

import java.util.ArrayList;
import java.util.List;

/**
 * Sequence builder
 */
public class SequenceBuilder {

    /**
     * The sequence
     */
    private final List<String> sequence = new ArrayList<>();

    /**
     * Create sequence builder
     */
    public SequenceBuilder() {
    }

    /**
     * Add simple worker
     *
     * @return Self instance
     */
    public SequenceBuilder addWorker() {
        sequence.add("W");
        return this;
    }

    /**
     * Add simple robot
     *
     * @return Self instance
     */
    public SequenceBuilder addRobot() {
        sequence.add("R");
        return this;
    }

    /**
     * Add simple machine
     *
     * @return Self instance
     */
    public SequenceBuilder addMachine() {
        sequence.add("M");
        return this;
    }

    /**
     * Build sequence
     *
     * @return The sequence as string list
     */
    public List<String> build() {
        return sequence;
    }
}
