package cz.cvut.fel.omo.smartfactory.utils;

/**
 * The job utils
 */
public class JobUtils {
    /**
     * Maximum job duration
     */
    public static final float MAX_DURATION = 100.0f;

    /**
     * Job start point
     */
    public static final float START_POINT = 0.0f;

    private JobUtils() {
    }

    /**
     * Calculate step duration
     *
     * @param steps The number of steps for completing the job
     * @return duration of a step
     */
    public static float stepDuration(int steps) {
        return MAX_DURATION / steps;
    }

}
