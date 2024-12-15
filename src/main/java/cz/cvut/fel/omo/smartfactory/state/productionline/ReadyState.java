package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.Series;

import java.util.List;

public final class ReadyState extends ProductionLineState {

    public ReadyState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean apply(Series series, List<ProductionUnit> sequence) {
        if (context.apply(series, sequence)) {
            context.setState(new ProcessingState(context));
            return true;
        }
        return false;
    }

    @Override
    public void process(long dt) {
        // nothing ...
    }

    @Override
    public Series pop() {
        return null;
    }
}
