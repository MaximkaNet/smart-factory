package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.state.ManufacturingEntityState;
import cz.cvut.fel.omo.smartfactory.state.ReadyState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractManufacturingEntity {

    private double electricityConsumption;
    private double oilConsumption;
    private double materialConsumption;
    private double usage;

    private ManufacturingEntityState state = new ReadyState();
}
