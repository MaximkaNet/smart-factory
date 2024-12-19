package cz.cvut.fel.omo.smartfactory.entity.productionline.productionline;

import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.series.Series;

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
