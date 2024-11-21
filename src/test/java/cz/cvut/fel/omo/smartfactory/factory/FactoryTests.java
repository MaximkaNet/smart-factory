package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FactoryTests {
    @Test
    public void runFactory() {
        Factory factory = new Factory();
        factory.startFactory();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(factory.stopFactory());
        assertFalse(factory.isRunning());
    }
}
