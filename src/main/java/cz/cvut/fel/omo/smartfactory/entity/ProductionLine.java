package cz.cvut.fel.omo.smartfactory.entity;

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

    public ProductionLine(String id, int priority, List<ProductionUnit> productionUnits) {
        this.id = id;
        this.priority = priority;
        this.productionUnits = productionUnits;
    }

    /**
     * Manufacture series (Set a series of products as soon as the line finishes producing the previous series)
     */
    public void applySeries(Series series) {
        if (producing || currentSeries.getProduced() != currentSeries.getCount()) {
            throw new RuntimeException("Cannot replace the current series during production");
        }

        List<String> sequence = series.getProduct().getSequence();
        ProductionUnit current = firstProductionUnit;
        for (String unitId : sequence) {
            ProductionUnit unit = productionUnits.stream().findFirst()
                    .orElseThrow(() -> new RuntimeException(
                            "Production unit " + unitId + " is not presented on the line " + id)
                    );

            if (firstProductionUnit == null) {
                firstProductionUnit = unit;
                current = unit;
                continue;
            }

            current.setNext(unit);
            current = unit;
        }
    }

    /**
     * Produce one product.
     * Can run in worker thread (the process may stop due to a malfunction of
     * the robot or machine, which will be waiting for repair)
     */
    public synchronized void process() {
        if (currentSeries == null) {
            // Send message ...
            throw new RuntimeException("Nothing to produce on the line " + id);
        }
        if (firstProductionUnit == null) {
            // Send message using event system ...
            throw new RuntimeException("No production units available to produce the series");
        }

        // call chain of responsibility
        firstProductionUnit.process(currentSeries.getProduct());
        // add produced product to current series of products
        currentSeries.newProducedProduct();

        if (currentSeries.getProduced() == currentSeries.getCount()) {
            // Send message to event system ...
            producing = false;
        }
    }

    /**
     * Returns true if production line is ready to producing
     */
    public boolean isReady() {
        return !producing;
    }
}
