package cz.cvut.fel.omo.smartfactory.order;

import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.productionline.ProductionLine;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The product order
 */
@Setter
@Getter
public class Order {
    /**
     * The order logger
     */
    private static final Logger LOGGER = LogManager.getLogger("Order");

    /**
     * The order name
     */
    private final Identifier id;

    /**
     * Number of products
     */
    private final long count;

    /**
     * Order status
     */
    private OrderStatus status;

    /**
     * Sequence for product
     */
    private final List<String> sequence;

    /**
     * Producing product
     */
    private ProductionLine productionLine;

    /**
     * Create order
     *
     * @param id       The product name
     * @param count    Count of products
     * @param sequence The sequence for producing
     */
    public Order(Identifier id, long count, List<String> sequence) {
        this.id = id;
        this.count = count;
        this.sequence = sequence;
        this.status = OrderStatus.CREATED;
    }
}
