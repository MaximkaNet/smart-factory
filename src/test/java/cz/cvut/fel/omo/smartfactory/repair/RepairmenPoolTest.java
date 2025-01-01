package cz.cvut.fel.omo.smartfactory.repair;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.equipment.state.BrokenState;
import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierManager;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.timer.FactoryTimer;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepairmenPoolTest {
    Factory factory;

    @BeforeEach
    void setUpAndCleanUp() {
        TimerManager.clear();
        EventBusManager.clear();

        RepairmenPool pool = new RepairmenPool();

        pool.createRepairman("Repairman 1", 1f);
        pool.createRepairman("Repairman 2", 1f);
        pool.createRepairman("Repairman 3", 1f);
        pool.createRepairman("Repairman 4", 1f);

        ProductionUnitManager productionUnitManager = new ProductionUnitManager();

        productionUnitManager.createWorker("Worker 1", JobUtils.stepDuration(1));
        productionUnitManager.createWorker("Worker 2", JobUtils.stepDuration(1));

        productionUnitManager.createRobot("Robot 1", 2000);
        productionUnitManager.createRobot("Robot 2", 2000);
        productionUnitManager.createMachine("Machine 1", 2000);
        productionUnitManager.createMachine("Machine 2", 2000);

        factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();
    }

    @Test
    void createRepairmenPoolAndBreakRobotAndRepairRobotPerOneHP() {
        IdentifierFactory repairmanIdFactory = IdentifierManager.getFactory("TEST_REPAIRMAN");
        IdentifierFactory robotIdFactory = IdentifierManager.getFactory("TEST_ROBOT");

        FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME, 1);
        EventBus eventBus = EventBusManager.getEventBus(Factory.EVENTBUS_NAME);

        List<Repairman> repairmen = List.of(new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f), new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f), new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f), new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f), new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f));

        RepairmenPool pool = new RepairmenPool(repairmen);

        eventBus.registerListener(EventType.OUTAGE, pool);

        Robot robot = new Robot(robotIdFactory.create("Manipulator 3000"), 200);

        robot.setState(new BrokenState(robot));
        robot.setActualHealth(198);
        eventBus.notifyListeners(new OutageEvent(robot, 1, timer.now()));

        pool.update(timer.getDeltaTime());

        assertEquals(199, robot.getActualHealth());
        assertEquals(BrokenState.class, robot.getState().getClass());
    }

    @Test
    public void factoryAssignsOutagesToRepairmanPool() {
        //setting the timer length
        TimerManager.getTimer(Factory.TIMER_NAME, 1000);

        // adding the order
        factory.addOrder("Tesla model X", 100, new ArrayList<>(Arrays.asList("R", "M", "W")));

        // simulating until all production lines are done working and all repairs are done
        for (int i = 0; i < 100; i++) {
            if (factory.getProductionLinePool().getProductionLineList().stream().allMatch(ProductionLine::isReady)) {
                if (factory.getRepairmanPool().getRepairmanList().stream().noneMatch(Repairman::isRepairing)) {
                    break;
                }
            }
            factory.simulate();
        }

        boolean allHealthy = factory.getProductionUnitManager()
                .getAvailableUnits()
                .values()
                .stream()
                .flatMap(List::stream)
                .noneMatch(AbstractProductionUnit::needRepair);

        assertTrue(allHealthy);
    }

    @Test
    public void repairmanPoolAssignsWorkToAllAvailableRepairmen() {
        //setting the timer length
        TimerManager.getTimer(Factory.TIMER_NAME, 1000);

        // set health to all Robots and Machines to 0
        factory.getProductionUnitManager()
                .getAvailableUnits()
                .forEach((type, units) -> {
                    if ("M".equals(type)) {
                        units.stream()
                                .filter(machine -> machine instanceof Machine)
                                .map(machine -> (Machine) machine)
                                .forEach(machine -> {
                                    machine.setActualHealth(0);
                                });
                    } else if ("R".equals(type)) {
                        units.stream()
                                .filter(robot -> robot instanceof Robot)
                                .map(robot -> (Robot) robot)
                                .forEach(robot -> {
                                    robot.setActualHealth(0);
                                });
                    }
                });

        // adding the order
        factory.addOrder("Tesla model X", 100, new ArrayList<>(Arrays.asList("M", "R", "M", "R", "W")));

        for (int i = 0; i < 100; i++) {
            if (factory.getRepairmanPool().getRepairmanList().stream().anyMatch(Repairman::isRepairing)) {
                break;
            }
            factory.simulate();
        }

        assertEquals(4, factory.getRepairmanPool().getRepairmanList().stream().filter(Repairman::isRepairing).count());
    }
}
