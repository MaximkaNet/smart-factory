package cz.cvut.fel.omo.smartfactory.identifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierFactoryTest {
    @Test
    void createFactoryAndGetTwoIdentifiers() {
        IdentifierFactory factory = new IdentifierFactory("TEST");

        Identifier id1 = factory.create("id");
        Identifier id2 = factory.create("id");

        assertEquals(1, id1.getNumber());
        assertEquals(2, id2.getNumber());

        assertEquals("id", id1.getName());
        assertEquals("id", id2.getName());
    }
}
