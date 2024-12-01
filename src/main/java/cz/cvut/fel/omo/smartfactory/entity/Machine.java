package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machine extends AbstractManufacturingEntity {

    protected Machine(String id, Consumption consumption, Price prices) {
        super(id, consumption, prices);
    }

    @Override
    public void process(Product product) {

    }
}
