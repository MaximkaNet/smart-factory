package cz.cvut.fel.omo.smartfactory.identifier;

import lombok.Getter;

/**
 * Immutable identifier
 */
@Getter
public class Identifier {

    /**
     * The identifier name
     */
    private final String name;

    /**
     * The short name
     */
    private final String shortName;

    /**
     * The identifier number
     */
    private final long number;

    /**
     * Create identifier
     *
     * @param number The unique number
     * @param name   The name
     */
    public Identifier(long number, String name, String shortName) {
        this.number = number;
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return name + " (" + number + ")";
    }
}
