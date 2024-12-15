package cz.cvut.fel.omo.smartfactory.entity.series;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import cz.cvut.fel.omo.smartfactory.entity.productionline.ProductionLine;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class OrderManager implements TickObserver {
    private static final Logger LOGGER = LogManager.getLogger("SeriesManager");

    /**
     * Incoming orders
     */
    private final List<Series> incoming = new ArrayList<>();

    /**
     * The factory reference
     */
    private final Factory factory;

    /**
     * Order manage constructor
     *
     * @param factory The factory
     */
    public OrderManager(Factory factory) {
        this.factory = factory;
    }

    /**
     * Add order to queue
     *
     * @param name    Order name
     * @param product Product template
     * @param count   Series of products
     */
    public void addSeries(String name, Product product, int count) {
        if (!factory.getProductionLinePool().isCompatible(product.getSequence())) {
            LOGGER.error("Cannot create series of products. Factory does not have enough production units.");
            return;
        }

        Date date = new Date();
        name += "_" + date.getTime();
        Series series = new Series(name, product, count);
        incoming.add(series);

        LOGGER.info("Series {} to incoming queue", name);
    }

    @Override
    public void update(long deltaTime) {
        incoming.removeIf(element -> {
            ProductionLine productionLine = factory.getProductionLinePool().tryConfigure(element);
            if (productionLine == null) {
                return false;
            } else {
                productionLine.setCurrentSeries(element);
                return true;
            }
        });
    }
}
