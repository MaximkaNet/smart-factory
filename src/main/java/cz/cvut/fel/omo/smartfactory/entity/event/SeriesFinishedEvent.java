package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import lombok.Getter;

import java.time.Instant;

@Getter
public class SeriesFinishedEvent extends FactoryEvent {
    ProductionLine productionLine;

    public SeriesFinishedEvent(Integer priority, ProductionUnit sender, Instant time, ProductionLine productionLine) {
        super(priority, sender, time);
        this.productionLine = productionLine;
    }
}
