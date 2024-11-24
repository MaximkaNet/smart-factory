package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
abstract public class Report {
    protected Factory factory;
    protected ZonedDateTime from;
    protected ZonedDateTime to;

    public Report(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        this.factory = factory;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", factory=" + factory + "]";
    }
}
