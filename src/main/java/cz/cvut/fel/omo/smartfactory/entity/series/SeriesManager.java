package cz.cvut.fel.omo.smartfactory.entity.series;

import cz.cvut.fel.omo.smartfactory.entity.Product;
import cz.cvut.fel.omo.smartfactory.entity.ProductionUnit;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factory.TickObserver;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

@Getter
public class SeriesManager implements TickObserver {

    private static final Logger LOGGER = LogManager.getLogger("SeriesManager");

    /**
     * Incoming orders
     */
    private final Queue<Series> incoming = new LinkedList<>();

    /**
     * Completed series
     */
    private final List<Series> completed = new ArrayList<>();

    /**
     * The factory reference
     */
    private final Factory factory;

    /**
     * Order manage constructor
     *
     * @param factory The factory
     */
    public SeriesManager(Factory factory) {
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
        if (!isCompatibleWithFactory(product.getSequence())) {
            LOGGER.error("Cannot create series of products. Factory does not have enough production units.");
            return;
        }

        Date date = new Date();
        name += "_" + date.getTime();
        Series series = new Series(name, product, count);
        incoming.add(series);

        LOGGER.info("Series " + name + " to incoming queue");
    }

    /**
     * Add completed series of products
     */
    public void addCompletedSeries(Series series) {
        completed.add(series);
    }

    @Override
    public void update(long deltaTime) {
        if (incoming.isEmpty()) {
            return;
        }

        Series series = incoming.peek();

        if (tryToConfigureProductionLine(series)) {
            incoming.poll();
        }
    }

    private boolean tryToConfigureProductionLine(Series series) {
        Map<Character, Integer> numberOfEachProductionUnit = getNumberOfEachProductionUnit(series.getProduct().getSequence());

        List<ProductionUnit> productionChain = new ArrayList<>();
        // Get available workers
        int workers = 0;

        return false;
    }

    /**
     * Returns true if the factory has enough equipment and labor, false otherwise
     *
     * @param sequence The sequence of production units
     */
    public boolean isCompatibleWithFactory(String sequence) {
        // Group sequence by type of production unit
        Map<Character, Integer> stat = getNumberOfEachProductionUnit(sequence);

        long countRobots = factory.getRobots().size();
        long countMachines = factory.getMachines().size();
        long countWorkers = factory.getPeople().stream().filter(person -> Objects.equals(person.getDiscriminator(), "W")).count();

        // Check number of production units one by group (robot, machine, worker)

        boolean hasEnoughUnits = true;

        if (stat.containsKey('R') && countRobots < stat.get('R')) {
            hasEnoughUnits = false;
        }

        if (stat.containsKey('M') && countMachines < stat.get('M')) {
            hasEnoughUnits = false;
        }

        if (stat.containsKey('W') && countWorkers < stat.get('W')) {
            hasEnoughUnits = false;
        }

        return hasEnoughUnits;
    }

    private Map<Character, Integer> getNumberOfEachProductionUnit(String sequence) {
        Map<Character, Integer> stat = new HashMap<>();

        for (char ch : sequence.toCharArray()) {
            int count = stat.getOrDefault(ch, 0);
            stat.put(ch, ++count);
        }

        return stat;
    }
}
