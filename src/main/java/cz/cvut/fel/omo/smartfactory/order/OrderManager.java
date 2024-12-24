package cz.cvut.fel.omo.smartfactory.order;

import cz.cvut.fel.omo.smartfactory.identifier.IdentifierFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Order manager
 */
@Getter
public class OrderManager {
    /**
     * Identifier factory
     */
    private static final IdentifierFactory identifierFactory = new IdentifierFactory("ORDER");

    /**
     * Order queue
     */
    private final Queue<Order> orderQueue = new LinkedList<>();

    /**
     * Resolved orders
     */
    private final List<Order> resolvedOrders = new ArrayList<>();

    /**
     * Add new order for production
     *
     * @param name     The order name
     * @param count    Count of products
     * @param sequence The sequence
     */
    public void addOrder(String name, long count, List<String> sequence) {
        orderQueue.offer(new Order(identifierFactory.create(name), count, sequence));
    }

    /**
     * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty
     */
    public Order peek() {
        return orderQueue.peek();
    }

    /**
     * Retrieves and removes the head of this queue, or returns null if this queue is empty.
     */
    public Order pop() {
        return orderQueue.poll();
    }

    /**
     * Get completed orders
     */
    public List<Order> getCompletedOrders() {
        return getOrdersByStatus(OrderStatus.COMPLETED);
    }

    /**
     * Get rejected orders
     */
    public List<Order> getRejectedOrders() {
        return getOrdersByStatus(OrderStatus.REJECTED);
    }

    /**
     * Get orders by status
     *
     * @param status The order status
     */
    private List<Order> getOrdersByStatus(OrderStatus status) {
        return resolvedOrders.stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }
}
