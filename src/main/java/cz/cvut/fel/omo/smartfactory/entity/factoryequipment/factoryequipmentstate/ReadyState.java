package cz.cvut.fel.omo.smartfactory.entity.factoryequipment.factoryequipmentstate;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.AbstractFactoryEquipment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadyState extends FactoryEquipmentState {

    public ReadyState(AbstractFactoryEquipment context) {
        super(context);
    }

    @Override
    public void process(long dt) {

    }

    @Override
    public boolean accept(Product product) {
        if (context.accept(product)) {
            context.setState(new RunningState(context));
            return true;
        }
        return false;
    }

    @Override
    public Product pop() {
        return null;
    }
}
