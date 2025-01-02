package cz.cvut.fel.omo.smartfactory.factory.iterator;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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

    @Test
    public void createFactoryAndIterate() {
        RepairmenPool pool = new RepairmenPool();
        pool.createRepairman("Repairman 1", 1f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();
        productionUnitManager.createWorker("Worker 1", JobUtils.stepDuration(1));
        productionUnitManager.createRobot("Robot 1", 2000);
        productionUnitManager.createMachine("Machine 1", 2000);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.getProductionUnitManager().getAvailableUnits().get("M").stream().
                filter(machine -> machine instanceof Machine)
                .map(machine -> (Machine) machine)
                .forEach(machine -> {
                    machine.setActualHealth(1000); // setting usage to (2000 - 1000) = 1000
                });

        factory.getProductionUnitManager().getAvailableUnits().get("R").stream().
                filter(robot -> robot instanceof Robot)
                .map(robot -> (Robot) robot)
                .forEach(robot -> {
                    robot.setActualHealth(800); // setting usage to (2000 - 800) = 1200
                });

        factory.addOrder("Test order 1", 10, new ArrayList<>(Arrays.asList("M", "R", "W")));

        FactoryIterator iterator = factory.getUsageIterator();

        assertTrue(iterator.hasNext());
        assertInstanceOf(Robot.class, iterator.next());
        assertTrue(iterator.hasNext());
        assertInstanceOf(Machine.class, iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next, "NEXT NOT FOUND");
    }
}
