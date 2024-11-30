package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductionLine {
    /**
     * The unique id
     */
    private final String id;

    /**
     * Production line priority
     */
    private int priority;

    /**
     * The production units such as person, machine, robot
     */
    private final List<ProductionUnit> productionUnits;

    /**
     * Current configuration (chain of responsibility)
     */
    private ProductionUnit firstProductionUnit;

    /**
     * Production state
     */
    private boolean producing = false;

    /**
     * The current produce series
     */
    private Series currentSeries = null;

    /**
     * The factory
     */
    private final Factory factory;

    public ProductionLine(Factory factory, String id, int priority, List<ProductionUnit> productionUnits) {
        this.factory = factory;
        this.id = id;
        this.priority = priority;
        this.productionUnits = productionUnits;
    }

    /**
     * Initialize series of products
     *
     * @param template Product template
     * @param id       The series id
     * @param count    Number of products
     */
    public void initSeries(Product template, String id, int count) {
//        if (isProducing()) {
//            throw new RuntimeException("Production line process on series");
//        }
//        // Check series id
//        if (factory.isSeriesExist(id)) {
//            // Send message ...
//            return;
//        }

        // Check product sequence
        // If sequence not compatible, reconfigure her
    }

    /**
     * Produce one product.
     * Can run in worker thread (the process may stop due to a malfunction of
     * the robot or machine, which will be waiting for repair)
     */
    public synchronized void process() {
//        if (currentSeries.isCompleted()) {
//            // Generate event
//            // add series to factory
//        }
//        if (currentSeries.hasTemplates()) {
//            Product template = currentSeries.getTemplate();
//            sequence.add(template);
//        }
//
//        sequence.update();
//
//        if (sequence.hasCompletedProduct()) {
//            Product product = sequence.getCompletedProduct();
//            currentSeries.addCompletedProduct(product);
//        }
    }

    /**
     * Returns true if production line is ready to producing
     */
    public boolean isReady() {
        return !producing;
    }
}
