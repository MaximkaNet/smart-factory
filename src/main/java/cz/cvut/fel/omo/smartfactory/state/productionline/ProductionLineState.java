package cz.cvut.fel.omo.smartfactory.state.productionline;

import cz.cvut.fel.omo.smartfactory.entity.ProductionLine;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.Series;

import java.util.List;

public abstract class ProductionLineState {
    protected final ProductionLine context;

    protected ProductionLineState(ProductionLine context) {
        this.context = context;
    }

    public abstract boolean apply(Series series, List<ProductionUnit> sequence);

    public abstract void process(long dt);

    public abstract Series pop();
}
