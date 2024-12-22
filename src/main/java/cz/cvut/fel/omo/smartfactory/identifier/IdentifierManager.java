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

    /**
     * Get identifier factory
     *
     * @param name The factory name
     * @return IdentifierFactory
     */
    public static IdentifierFactory getFactory(String name) {
        if (!factories.containsKey(name)) {
            factories.put(name, new IdentifierFactory(name));
        }
        return factories.get(name);
    }
}
