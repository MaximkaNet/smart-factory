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

    /**
     * @param number    The unique number
     * @param name      The name
     * @param shortName The short name of the entity
     */
    public Identifier(long number, String name, String shortName) {
        this.number = number;
        this.name = name;
        this.shortName = shortName;
    }

    /**
     * Create Identifier
     *
     * @param number    The unique number
     * @param name      The name
     * @param shortName The short name of the entity
     * @return New Identifier
     */
    public static Identifier of(long number, String name, String shortName) {
        return new Identifier(number, name, shortName);
    }

    /**
     * Create Identifier
     *
     * @param number The unique number
     * @param name   The name
     * @return New Identifier
     */
    public static Identifier of(long number, String name) {
        return Identifier.of(number, name, String.valueOf(name.charAt(0)));
    }

    /**
     * Create Identifier
     *
     * @param number The unique number
     * @return New Identifier
     */
    public static Identifier of(long number) {
        return Identifier.of(number, " ");
    }

    @Override
    public String toString() {
        return name + " (" + number + ")";
    }
}
