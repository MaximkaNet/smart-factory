package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionline.state.ProductionLineState;
import cz.cvut.fel.omo.smartfactory.productionline.state.ReadyState;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The production line
 */
@Getter
@Setter
public class ProductionLine {
    /**
     * Production line logger
     */
    private static final Logger LOGGER = LogManager.getLogger("Production line");

    /**
     * Current configuration (chain of responsibility)
     */
    private AbstractProductionUnit chain;

    /**
     * Input template
     */
    private Product inputTemplate;

    /**
     * Released product
     */
    private Product releasedProduct;

    /**
     * Production line state
     */
    private ProductionLineState state = new ReadyState(this);

    /**
     * Create production line
     */
    public ProductionLine(AbstractProductionUnit chain) {
        this.chain = chain;
    }

    /**
     * Accept product template for processing
     *
     * @param template The product template
     * @return True if input slot is empty false otherwise
     */
    public boolean accept(Product template) {
        if (inputTemplate != null) {
            return false;
        }
        inputTemplate = template;
        return true;
    }

    /**
     * Process production
     */
    public void process(long dt) {
        if (releasedProduct != null) {
            LOGGER.warn("The production line cannot handle the chain due to the released product");
            return;
        }

        // Accept product template and update chain
        Product released = chain.processChain(inputTemplate, dt);

        // Release processed product
        if (released != null) {
            releasedProduct = released;
        }
    }

    /**
     * Returns released product
     */
    public Product pop() {
        Product out = releasedProduct;
        releasedProduct = null;
        return out;
    }

    /**
     * Returns released product
     */
    public Product peek() {
        return releasedProduct;
    }

    /**
     * Returns true if production line has released product
     */
    public boolean productIsDone() {
        return releasedProduct != null;
    }
}
