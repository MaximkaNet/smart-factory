package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.factory.Behavioral;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Getter
public class SeriesManager implements Behavioral {

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
            throw new RuntimeException("Factory does not have enough production units.");
        }

        Date date = new Date();
        name += "_" + date.getTime();
        Series series = new Series(name, product, count);
        incoming.add(series);
    }

    /**
     * Add completed series of products
     */
    public void addCompletedSeries(Series series) {
        completed.add(series);
    }

    @Override
    public void update(long deltaTime) {
        Series series = incoming.peek();

        // Get compatible production line
        ProductionLine line = null;

        if (line != null) {
            line.applySeries(series);
        }
    }

    /**
     * Returns true if the factory has enough equipment and labor, false otherwise
     *
     * @param sequence The sequence of production units
     */
    public boolean isCompatibleWithFactory(String sequence) {
        // Group sequence by type of production unit
        Map<Character, Integer> stat = new HashMap<>();

        for (char ch : sequence.toCharArray()) {
            int count = stat.getOrDefault(ch, 0);
            stat.put(ch, ++count);
        }

        int countRobots = factory.getRobots().size();
        int countMachines = factory.getMachines().size();
        int countWorkers = factory.getPeople().stream().filter(person -> person.getDescriminator() == "W").count();

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
}
