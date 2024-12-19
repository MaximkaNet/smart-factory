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

    public Report(Factory factory, ZonedDateTime from, ZonedDateTime to) {
        this.factory = factory;
        this.from = from;
        this.to = to;
    }

    /**
     * Export report to json
     */
    public abstract String exportJson();

    
}
