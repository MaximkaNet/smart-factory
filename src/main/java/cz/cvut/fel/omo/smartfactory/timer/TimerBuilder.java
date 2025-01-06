package cz.cvut.fel.omo.smartfactory.timer;

import java.time.Instant;
import java.time.ZoneId;

/**
 * The timer builder
 */
public class TimerBuilder {
    /**
     * The foundation time
     */
    private Instant foundationDate = Instant.MIN;

    /**
     * Tick length milliseconds
     */
    private long tickLength = 1000 * 60 * 60;

    /**
     * Tick length realtime milliseconds
     */
    private long tickLengthRealtime = 1000;

    /**
     * Timer timezone
     */
    private ZoneId timezone = ZoneId.systemDefault();

    /**
     * Create timer builder
     */
    public TimerBuilder() {
    }

    /**
     * Set foundation date
     *
     * @param date The foundation date
     */
    public TimerBuilder setFoundationDate(Instant date) {
        if (date != null) {
            this.foundationDate = date;
        }
        return this;
    }

    /**
     * Set tick length
     *
     * @param milli The length in milliseconds
     */
    public TimerBuilder setTickLength(long milli) {
        if (milli > 0) {
            this.tickLength = milli;
        }
        return this;
    }

    /**
     * Set realtime tick delay
     *
     * @param milli The length in milliseconds
     */
    public TimerBuilder setTickLengthRealtime(long milli) {
        if (milli > 0) {
            this.tickLengthRealtime = milli;
        }
        return this;
    }

    /**
     * Set timezone
     *
     * @param zoneId Zone id
     */
    public TimerBuilder setTimezone(ZoneId zoneId) {
        if (zoneId != null) {
            this.timezone = zoneId;
        }
        return this;
    }

    /**
     * Build the FactoryTimer
     *
     * @return FactoryTimer built
     */
    public FactoryTimer build() {
        return new FactoryTimer(foundationDate, tickLength, tickLengthRealtime, timezone);
    }
}
