package cz.cvut.fel.omo.smartfactory.entity.report;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Report {
    protected Factory factory;

    public Report(Factory factory) {
        this.factory = factory;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", factory=" + factory + "]";
    }
}
