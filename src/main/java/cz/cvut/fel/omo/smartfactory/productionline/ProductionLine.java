package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
     * Input queue
     */
    private Queue<Product> inputQueue = new LinkedList<>();

    /**
     * Output stack
     */
    private Stack<Product> outputStack = new Stack<>();

    /**
     * Items in progress
     */
    private long inProgress = 0;

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
     */
    public void addTemplate(Product template) {
        inputQueue.add(template);
        inProgress++;
    }

    /**
     * Process production
     */
    public void process(long dt) {
        if (inProgress == 0) {
            return;
        }

        // Accept product template and update chain
        if (chain.getState().accept(inputQueue.peek())) {
            inputQueue.poll();
        }

        Product released = chain.processChain(dt);

        // Release processed product
        if (released != null) {
            inProgress--;
            outputStack.push(released);
        }
    }

    /**
     * Returns released product
     */
    public Product pop() {
        if (outputStack.empty()) {
            return null;
        }
        return outputStack.pop();
    }

    /**
     * Returns released product
     */
    public Product peek() {
        if (outputStack.empty()) {
            return null;
        }
        return outputStack.peek();
    }

    public boolean hasReleasedProduct() {
        return !outputStack.empty();
    }
}
