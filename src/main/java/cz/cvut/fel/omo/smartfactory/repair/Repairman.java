package cz.cvut.fel.omo.smartfactory.repair;

import cz.cvut.fel.omo.smartfactory.equipment.AbstractEquipment;
import cz.cvut.fel.omo.smartfactory.event.EventBus;
import cz.cvut.fel.omo.smartfactory.event.EventBusManager;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.timer.FactoryTimer;
import cz.cvut.fel.omo.smartfactory.timer.TimerManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Repair unit for factory equipments
 */
@Getter
@Setter
public class Repairman {
    private static final Logger LOGGER = LogManager.getLogger("Repairman");

    /**
     * The name
     */
    private final Identifier id;

    /**
     * Repair power
     */
    private final float power;

    /**
     * Event is being processed now
     */
    private OutageEvent outageEvent;

    /**
     * The repair subject
     */
    private AbstractEquipment subject;

    /**
     * Create repairman
     *
     * @param id    The repair man identifier
     * @param power The repair power per tick
     */
    public Repairman(Identifier id, float power) {
        this.id = id;
        this.power = power;
    }

    /**
     * Accept outage event
     *
     * @param event The outage event
     * @return true if accepted
     */
    public boolean accept(OutageEvent event) {
        if (event == null || outageEvent != null || subject != null) {
            return false;
        }

        LOGGER.info("Repairman: {} started working on: {}", this, event);

        outageEvent = event;
        subject = outageEvent.getSender();
        // Generate repair started event
        FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME);
        EventBus eventBus = EventBusManager.getEventBus(Factory.TIMER_NAME);

        eventBus.notifyListeners(new RepairStartedEvent(id, timer.now()));
        outageEvent.repairStarted(timer.now());
        return true;
    }

    /**
     * Process the time delta
     *
     * @param deltaTime delta of time from factory
     */
    public void process(long deltaTime) {
        if (!isRepairing()) {
            return;
        }

        boolean isRepaired = subject.getState().repair(power * deltaTime);

        if (isRepaired) {
            // Generate finished event
            FactoryTimer timer = TimerManager.getTimer(Factory.TIMER_NAME);
            EventBus eventBus = EventBusManager.getEventBus(Factory.TIMER_NAME);

            eventBus.notifyListeners(new RepairFinishedEvent(id, timer.now()));
            outageEvent.repairFinished(timer.now());

            // Reset subject and event
            stopRepair();
        }
    }

    /**
     * To check if repairman is performing a repair
     *
     * @return true if repairs
     */
    public boolean isRepairing() {
        return subject != null && outageEvent != null;
    }

    /**
     * stop repairing immediately
     */
    private void stopRepair() {
        LOGGER.info("Repairman: {} stopped repairing: {}", this, outageEvent);

        subject = null;
        outageEvent = null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{id=" + getId() + "}";
    }
}
