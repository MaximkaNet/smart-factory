package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.Series;

public abstract class ProductionLineState {
    protected final ProductionLine context;

    protected ProductionLineState(ProductionLine context) {
        this.context = context;
    }

    public abstract boolean apply(Series series);

    public abstract void process(long dt);

    public abstract Series pop();
}
