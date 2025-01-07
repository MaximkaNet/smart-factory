package cz.cvut.fel.omo.smartfactory.identifier;

import lombok.Getter;

/**
 * The identifier factory
 */
@Getter
public class IdentifierFactory {
    /**
     * The factory name
     */
    private final String name;

    /**
     * Last number
     */
    private long lastNumber;

    /**
     * The identity for generation unique values
     */
    private final long identity;

    /**
     * Create identifier factory with identity 1
     *
     * @param name The factory name
     */
    public IdentifierFactory(String name) {
        this(name, 1);
    }

    /**
     * Create identifier factory with specified identity
     *
     * @param name     The factory name
     * @param identity The identity
     */
    public IdentifierFactory(String name, long identity) {
        this.identity = identity;
        this.name = name;
        this.lastNumber = 0;
    }

    /**
     * Create identifier
     *
     * @param name The object name. For instance "Robot", "Manipulator 300", "Machine 2897"
     * @return The identifier with unique number
     */
    public Identifier create(String name) {
        return create(name, String.valueOf(name.charAt(0)));
    }

    /**
     * Create unique identifier in this factory
     *
     * @param name      The object name
     * @param shortName Short name of the entity
     * @return Created Identifier
     */
    public Identifier create(String name, String shortName) {
        lastNumber += identity;
        return new Identifier(lastNumber, name, shortName);
    }
}
