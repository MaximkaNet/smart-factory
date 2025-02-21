package cz.cvut.fel.omo.smartfactory.timer;

import java.util.HashMap;
import java.util.Map;

/**
 * Timer manager
 */
public class TimerManager {

    /**
     * The default tick length in milliseconds
     */
    public static final long DEFAULT_TICK_LENGTH = 200;

    /**
     * The timers
     */
    private static final Map<String, FactoryTimer> timers = new HashMap<>();

    private TimerManager() {
    }

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
     * @param name       The timer name
     * @param tickLength length of a tick
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
