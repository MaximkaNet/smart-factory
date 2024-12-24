package cz.cvut.fel.omo.smartfactory.repair;

import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.equipment.state.BrokenState;
import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import cz.cvut.fel.omo.smartfactory.identifier.IdentifierManager;
import cz.cvut.fel.omo.smartfactory.timer.FactoryTimer;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepairmenPoolTest {
    @BeforeEach
    void cleanup() {
        TimerManager.clear();
        EventBusManager.clear();
    }

    @Test
    void createRepairmenPoolAndBreakRobotAndRepairRobotPerOneHP() {
        IdentifierFactory repairmanIdFactory = IdentifierManager.getFactory("TEST_REPAIRMAN");
        IdentifierFactory robotIdFactory = IdentifierManager.getFactory("TEST_ROBOT");

        FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME, 1);
        EventBus eventBus = EventBusManager.getEventBus(Factory.EVENTBUS_NAME);

        List<Repairman> repairmen = List.of(
                new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f),
                new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f),
                new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f),
                new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f),
                new Repairman(repairmanIdFactory.create("Repairman test"), 1.0f)
        );

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
}
