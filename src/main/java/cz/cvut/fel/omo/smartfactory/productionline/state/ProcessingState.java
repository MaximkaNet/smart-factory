package cz.cvut.fel.omo.smartfactory.productionline.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;

/**
 * Production line processing state
 */
public final class ProcessingState extends ProductionLineState {
    /**
     * Create production line state
     *
     * @param context The context
     */
    public ProcessingState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean accept(Product template) {
        return false;
    }

    @Override
    public void process(long dt) {
        context.process(dt);
        if (context.productIsDone()) {
            context.setState(new FinishedState(context));
        }
    }

    @Override
    public Product pop() {
        return null;
    }

    @Override
    public Product peek() {
        return context.peek();
    }
}
