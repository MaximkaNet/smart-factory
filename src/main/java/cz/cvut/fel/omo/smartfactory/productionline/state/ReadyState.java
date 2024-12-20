package cz.cvut.fel.omo.smartfactory.productionline.state;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;

/**
 * Production line ready state
 */
public final class ReadyState extends ProductionLineState {
    /**
     * Create production line state
     *
     * @param context The context
     */
    public ReadyState(ProductionLine context) {
        super(context);
    }

    @Override
    public boolean accept(Product template) {
        if (context.accept(template)) {
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
    public Product pop() {
        return null;
    }


    @Override
    public Product peek() {
        return context.peek();
    }
}
