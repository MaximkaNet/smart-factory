package cz.cvut.fel.omo.smartfactory.timer;

import java.util.HashMap;
import java.util.Map;

/**
 * Timer manager
 */
public class TimerManager {

    private static final Map<String, FactoryTimer> timers = new HashMap<>();

    /**
     * Add timer
     *
     * @param name  The timer name
     * @param timer The timer
     */
    public static void addTimer(String name, FactoryTimer timer) {
        timers.put(name, timer);
    }

    /**
     * Get timer by name
     *
     * @param timer The timer name
     * @return FactoryTimer or NULL if timer not found
     */
    public static FactoryTimer getTimer(String timer) {
        return timers.getOrDefault(timer, null);
    }
}
