package cz.cvut.fel.omo.smartfactory.timer;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
public class FactoryTimer {

    /**
     * Time inside simulation
     */
    private Instant now;

    /**
     * Instant of foundation date
     */
    private final Instant foundationDate;

    /**
     * Tick length
     */
    @Setter
    private long tickLength;

    /**
     * Realtime tick length
     */
    @Setter
    private long realtimeTickDelay;

    /**
     * Simulation time zone
     */
    @Setter
    private ZoneId timezone;

    /**
     * New day flag
     */
    private boolean isNewDay;

    /**
     * @param tickLengthMilli tick length for factory
     */
    public FactoryTimer(long tickLengthMilli) {
        this(Instant.EPOCH, tickLengthMilli);
    }

    /**
     * @param foundationDate  start date of the factory
     * @param tickLengthMilli tick length in milliseconds
     */
    public FactoryTimer(Instant foundationDate, long tickLengthMilli) {
        this(foundationDate, tickLengthMilli, ZoneId.systemDefault());
    }

    /**
     * @param foundationDate  start date of the factory
     * @param tickLengthMilli tick length in milliseconds
     * @param timezone        timezone of the factory
     */
    public FactoryTimer(Instant foundationDate, long tickLengthMilli, ZoneId timezone) {
        this(foundationDate, tickLengthMilli, tickLengthMilli, timezone);
    }

    /**
     * @param foundationDate          start date of the factory
     * @param tickLengthMilli         tick length in milliseconds
     * @param timezone                timezone of the factory
     * @param realtimeTickLengthMilli real tick length in milliseconds
     */
    public FactoryTimer(Instant foundationDate, long tickLengthMilli, long realtimeTickLengthMilli, ZoneId timezone) {
        this.foundationDate = foundationDate;
        this.tickLength = tickLengthMilli;
        this.realtimeTickDelay = realtimeTickLengthMilli;
        this.timezone = timezone;
        this.isNewDay = false;
        this.now = foundationDate;
    }

    /**
     * Returns new instance of TimerBuilder
     *
     * @return builder for FactoryTimer
     */
    public static TimerBuilder builder() {
        return new TimerBuilder();
    }

    /**
     * Handle new timer tick
     */
    public void tick() {
        Instant prev = now;

        // Change now time
        now = now.plusMillis(tickLength);

        LocalDate prevDate = now.atZone(timezone).toLocalDate();
        LocalDate currentDate = prev.atZone(timezone).toLocalDate();

        // Process new day flag
        isNewDay = !prevDate.equals(currentDate);
    }

    /**
     * Returns day number from foundation day
     *
     * @return number of the current day
     */
    public long dayNumber() {

        long nowEpochDay = now.atZone(timezone).toLocalDate().toEpochDay();
        long foundationEpochDay = foundationDate.atZone(timezone).toLocalDate().toEpochDay();

        return nowEpochDay - foundationEpochDay;
    }

    /**
     * @return time inside simulation
     */
    public Instant now() {
        return now;
    }

    public long getDeltaTime() {
        return tickLength;
    }
}
