package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;

import java.time.ZonedDateTime;

public class FactoryConfigurationReport extends Report{

    public FactoryConfigurationReport(ZonedDateTime from, ZonedDateTime to, Factory factory) {
        super(factory, from, to);
    }

    @Override
    public String exportJson() {
        return "";
    }
}
