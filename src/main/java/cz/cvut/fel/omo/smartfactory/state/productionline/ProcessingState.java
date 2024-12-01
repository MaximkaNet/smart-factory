package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Series;

public final class ProcessingState extends ProductionLineState {

    public ProcessingState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean applySeries(Series series) {
        // TODO Throw exception ?
        return false;
    }

    @Override
    public void update() {
        context.update();
        if (context.getCurrentSeries().isDone()) {
            context.setProducing(false);
            context.setState(new ReadyState(context));
        }
    }
}
