package cz.cvut.fel.omo.smartfactory.productionline;

import cz.cvut.fel.omo.smartfactory.Product;
import cz.cvut.fel.omo.smartfactory.productionunit.AbstractProductionUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    /**
     * Create production unit
     *
     * @param productionUnits The sequence of production units
     * @return ProductionLine or NULL if production unit list is empty
     */
    public static ProductionLine create(List<AbstractProductionUnit> productionUnits) {
        if (productionUnits.isEmpty()) {
            return null;
        }
        return new ProductionLine(createChain(productionUnits));
    }

    /**
     * Reset the chain
     *
     * @param line The production line
     */
    public static List<AbstractProductionUnit> reset(ProductionLine line) {
        if (line == null) {
            return new ArrayList<>();
        }
        
        AbstractProductionUnit chain = line.getChain();

        if (chain == null) {
            return new ArrayList<>();
        }

        List<AbstractProductionUnit> out = new ArrayList<>();
        AbstractProductionUnit current = chain;

        while (current != null) {
            AbstractProductionUnit unit = current.getNext();
            current.setNext(null);
            out.add(current);
            current = unit;
        }

        line.setChain(null);

        return out;
    }

    /**
     * Create chain of responsibility
     *
     * @param units The chain of production units
     * @return First production unit in chain or NULL if unit list is empty
     */
    private static AbstractProductionUnit createChain(List<AbstractProductionUnit> units) {
        AbstractProductionUnit current = null;
        AbstractProductionUnit first = null;

        for (AbstractProductionUnit unit : units) {
            if (first == null) {
                first = unit;
                current = unit;
                continue;
            }
            current.setNext(unit);
            current = unit;
        }

        return first;
    }
}
