package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import java.time.ZonedDateTime;

public class ConsumptionReport extends Report{

    public ConsumptionReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        super(from, to, factory);
    }
}
