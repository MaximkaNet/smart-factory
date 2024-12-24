package cz.cvut.fel.omo.smartfactory.order;

/**
 * Order status
 */
public enum OrderStatus {
    /**
     * Created status
     */
    CREATED,

    /**
     * Accepted status
     */
    ACCEPTED,

    /**
     * Rejected status (if sequence is not compatible)
     */
    REJECTED,

    /**
     * In progress status (producing)
     */
    IN_PROGRESS,

    /**
     * Completed status
     */
    COMPLETED
}
