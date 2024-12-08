package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Series;

public final class FinishedState extends ProductionLineState {

    public FinishedState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean apply(Series series) {
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
