package cz.cvut.fel.omo.smartfactory.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Robot extends AbstractManufacturingEntity {

    protected Robot(String id, Consumption consumption, Price prices) {
        super(id, consumption, prices);
    }

    @Override
    public void process(Product product) {

    }
}
