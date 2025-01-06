package cz.cvut.fel.omo.smartfactory.utils;

public class JobUtils {
    public static final float MAX_DURATION = 100.0f;
    public static final float START_POINT = 0.0f;

    /**
     * Calculate step duration
     *
     * @return duration of a step
     */
    public static float stepDuration(int steps) {
        return 100.0f / steps;
    }

}
