package cz.cvut.fel.omo.smartfactory.productionline.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;

/**
 * Production line finished state
 */
public final class FinishedState extends ProductionLineState {
    /**
     * Create production line state
     *
     * @param context The context
     */
    public FinishedState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean accept(Product template) {
        return false;
    }

    @Override
    public void process(long dt) {
        // Nothing
    }

    @Override
    public Product pop() {
        context.setState(new ReadyState(context));
        return context.pop();
    }

    @Override
    public Product peek() {
        return context.peek();
    }
}
