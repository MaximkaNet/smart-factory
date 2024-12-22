package cz.cvut.fel.omo.smartfactory.identifier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierManagerTest {
    @Test
    void getFactoryWithDefaultIdentity() {
        // Name must be unique
        IdentifierFactory factory = IdentifierManager.getFactory("TEST");

        assertEquals("TEST", factory.getName());
        assertEquals(0, factory.getLastNumber());
        assertEquals(1, factory.getIdentity());
    }

    @Test
    void getFactoryWithSpecifiedIdentity() {
        // Name must be unique
        IdentifierFactory factory = IdentifierManager.getFactory("TEST2", 2);

        assertEquals("TEST2", factory.getName());
        assertEquals(0, factory.getLastNumber());
        assertEquals(2, factory.getIdentity());
    }

    @Test
    void getFactoryAndCreateTwoIdentifiers() {
        // Name must be unique
        IdentifierFactory factory = IdentifierManager.getFactory("TEST3", 2);

        factory.create("id");
        factory.create("id");

        assertEquals("TEST3", factory.getName());
        assertEquals(4, factory.getLastNumber());
        assertEquals(2, factory.getIdentity());
    }
}
