package cz.cvut.fel.omo.smartfactory.entity.productionline.productionline;

import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.series.Series;

import java.util.List;

public final class FinishedState extends ProductionLineState {

    public FinishedState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean apply(Series series, List<ProductionUnit> sequence) {
        return false;
    }

    @Override
    public void process(long dt) {
        // Nothing
    }

    @Override
    public Series pop() {
        Series series = context.pop();
        context.setState(new ReadyState(context));
        return series;
    }
}
