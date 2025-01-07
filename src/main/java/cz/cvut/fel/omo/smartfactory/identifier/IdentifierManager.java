package cz.cvut.fel.omo.smartfactory.identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * The identifier manager
 */
public class IdentifierManager {

    /**
     * Identifier factories
     */
    private static final Map<String, IdentifierFactory> factories = new HashMap<>();

    private IdentifierManager() {
    }

    /**
     * Get identifier factory with identity 1
     *
     * @param name The factory name
     * @return IdentifierFactory
     */
    public static IdentifierFactory getFactory(String name) {
        return getFactory(name, 1);
    }

    /**
     * Get identifier factory with specified identity
     *
     * @param name     The factory name
     * @param identity The identity
     * @return IdentifierFactory
     */
    public static IdentifierFactory getFactory(String name, long identity) {
        if (!factories.containsKey(name)) {
            factories.put(name, new IdentifierFactory(name, identity));
        }
        return factories.get(name);
    }
}
