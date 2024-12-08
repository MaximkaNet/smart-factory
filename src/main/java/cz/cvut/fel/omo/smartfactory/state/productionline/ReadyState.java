package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Series;

public final class ReadyState extends ProductionLineState {

    public ReadyState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean apply(Series series) {
        if (context.apply(series)) {
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
