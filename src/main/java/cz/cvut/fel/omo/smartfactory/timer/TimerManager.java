package cz.cvut.fel.omo.smartfactory.timer;

import java.util.HashMap;
import java.util.Map;

/**
 * Timer manager
 */
public class TimerManager {

    public static final long DEFAULT_TICK_LENGTH = 200;

    private static final Map<String, FactoryTimer> timers = new HashMap<>();

    /**
     * Get timer by name
     *
     * @param timer The timer name
     * @return FactoryTimer or NULL if timer not found
     */
    public static FactoryTimer getTimer(String timer) {
        return timers.computeIfAbsent(timer, k -> new FactoryTimer(DEFAULT_TICK_LENGTH));
    }

    /**
     * Get timer by name
     *
     * @param name The timer name
     * @return FactoryTimer or NULL if timer not found
     */
    public static FactoryTimer getTimer(String name, long tickLength) {
        return timers.computeIfAbsent(name, k -> new FactoryTimer(tickLength));
    }

    /**
     * Clear timers
     */
    public static void clear() {
        timers.clear();
    }
}
