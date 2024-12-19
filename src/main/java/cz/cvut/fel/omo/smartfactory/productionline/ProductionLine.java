package cz.cvut.fel.omo.smartfactory.entity.productionline;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.productionline.productionline.ProductionLineState;
import cz.cvut.fel.omo.smartfactory.entity.productionline.productionline.ReadyState;
import cz.cvut.fel.omo.smartfactory.entity.productionunit.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.storage.Storage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionLine {

    /**
     * Current configuration (chain of responsibility)
     */
    private ProductionUnit chain;

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
     * Input storage pointer
     */
    private Storage<Product> inStorage;

    /**
     * Output storage pointer
     */
    private Storage<Product> outStorage;

    /**
     * Create production line
     */
    public ProductionLine(ProductionUnit chain) {
        this(chain, null, null);
    }

    /**
     * Create production line and connect in/out storage
     *
     * @param chain The production unit chain
     * @param in    Input storage
     * @param out   Output storage
     */
    public ProductionLine(ProductionUnit chain, Storage<Product> in, Storage<Product> out) {
        this.chain = chain;
        this.inStorage = in;
        this.outStorage = out;
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
    protected void process(long dt) {
        // Input storage hook
        if (inputTemplate == null && inStorage != null) {
            inputTemplate = inStorage.pop(1);
        }

        // Accept product template
        if (chain.accept(inputTemplate)) {
            inputTemplate = null;
        }

        chain.process(dt);

        // Release processed product
        Product out = chain.pop();
        if (out != null) {
            releasedProduct = out;
        }

        // Output storage hook
        if (releasedProduct != null && outStorage != null) {
            outStorage.push(releasedProduct);
            releasedProduct = null;
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

    public boolean isFinished() {
        return false;
    }

    public boolean isWaitForTemplate() {
        return false;
    }

    public boolean isProcessing() {
        return false;
    }

    public boolean isWaitForPickUp() {
        return false;
    }
}
