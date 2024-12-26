package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsageIteratorTest {

    /**
     * Get equipment list
     *
     * @param actualHealthList The list for equipment
     */
    private static List<AbstractEquipment> getEquipmentList(List<Float> actualHealthList) {

        IdentifierFactory factory = new IdentifierFactory("");

        List<AbstractEquipment> out = new ArrayList<>();

        for (float actualHealth : actualHealthList) {
            Robot robot = new Robot(factory.create("Robot"), 100);
            robot.setActualHealth(actualHealth);
            out.add(robot);
        }

        return out;
    }

    /**
     * Get comparator
     */
    private static Comparator<AbstractEquipment> getComparator() {
        return Comparator.comparingDouble(
                (AbstractEquipment equipment) -> (equipment.getMaximumHealth() - equipment.getActualHealth()) / equipment.getMaximumHealth()
        );
    }

    @Test
    void test() {
        List<AbstractEquipment> equipmentList = getEquipmentList(List.of(
                20.0f,
                50.7f,
                12.05f,
                90.0f
        ));

        List<AbstractEquipment> sortedByPercentage = equipmentList.stream()
                .sorted(getComparator())
                .toList();

        FactoryIterator iterator = new UsageIterator(equipmentList);

        assertTrue(iterator.hasNext());
        for (AbstractEquipment equipment : sortedByPercentage) {
            assertEquals(equipment, iterator.next());
        }
        assertFalse(iterator.hasNext());

        Throwable throwable = assertThrows(NoSuchElementException.class, iterator::next);
        assertEquals("The iterator has no next element", throwable.getMessage());
    }
}
