package cz.cvut.fel.omo.smartfactory.identifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierTest {
    @Test
    void createSimpleIdentifier() {
        Identifier identifier = new Identifier(1, "id", "ID");

        assertEquals(1, identifier.getNumber());
        assertEquals("id", identifier.getName());
        assertEquals("id (1)", identifier.toString());
    }
}
