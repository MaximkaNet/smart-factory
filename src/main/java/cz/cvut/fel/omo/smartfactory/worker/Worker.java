package cz.cvut.fel.omo.smartfactory.worker;

import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import cz.cvut.fel.omo.smartfactory.productionunit.state.ReadyState;
import cz.cvut.fel.omo.smartfactory.productionunit.state.factory.StateFactory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractWorker extends AbstractProductionUnit {
    public AbstractWorker(String name) {
        super(name);

        setState(new ReadyState(this, new StateFactory()));
    }

//    @Override
//    public void acceptVisitor(FactoryVisitor visitor) {
////        visitor.visit(this);
//    }
}
