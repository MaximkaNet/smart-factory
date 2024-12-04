package cz.cvut.fel.omo.smartfactory.entity;

import cz.cvut.fel.omo.smartfactory.entity.factory.Behavioral;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
public class OrderManager implements Behavioral {

    /**
     * Incoming orders
     */
    private final Queue<Series> incoming = new LinkedList<>();

    /**
     * Completed series
     */
    private final List<Series> completed = new ArrayList<>();

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
    public void addOrder(String name, Product product, int count) {

        if (!isCompatibleWithFactory(product.getSequence())) {
            throw new RuntimeException("Factory does not have enough equipment or labor.");
        }

        Date date = new Date();
        name += "_" + date.getTime();
        Series series = new Series(name, product, count);
        incoming.add(series);
    }

    @Override
    public void update(long deltaTime) {

    }

    /**
     * Returns true if the factory has enough equipment and labor, false otherwise
     *
     * @param sequence The sequence of production units
     */
    public boolean isCompatibleWithFactory(String sequence) {

        // Group sequence by type of production unit
        // Check number of production units one by group (robot, machine, worker)

        return false;
    }
}
