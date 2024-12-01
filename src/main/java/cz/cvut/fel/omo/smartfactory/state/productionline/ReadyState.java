package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Series;

public final class ReadyState extends ProductionLineState {

    public ReadyState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean applySeries(Series series) {
        if (context.applySeries(series)) {
            context.setState(new ProcessingState(context));
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}
