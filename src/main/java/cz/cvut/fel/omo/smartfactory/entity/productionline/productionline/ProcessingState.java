package cz.cvut.fel.omo.smartfactory.entity.productionline.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.series.Series;

import java.util.List;

public final class ProcessingState extends ProductionLineState {

    public ProcessingState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean apply(Series series, List<ProductionUnit> sequence) {
        // TODO Throw exception ?
        return false;
    }

    @Override
    public void process(long dt) {
        context.update(dt);
        if (context.getCurrentSeries().isDone()) {
            context.setProducing(false);
            context.setState(new ReadyState(context));
        }
    }

    @Override
    public Series pop() {
        return null;
    }
}
