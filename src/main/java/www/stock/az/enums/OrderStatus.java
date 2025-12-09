package www.stock.az.enums;

/**
 * Order status enumeration
 */
public enum OrderStatus {
    /**
     * Order is pending
     */
    PENDING,

    /**
     * Order is confirmed
     */
    CONFIRMED,

    /**
     * Order is being processed
     */
    PROCESSING,

    /**
     * Order is ready for shipping
     */
    READY_FOR_SHIPPING,

    /**
     * Order is shipped
     */
    SHIPPED,

    /**
     * Order is delivered
     */
    DELIVERED,

    /**
     * Order is completed
     */
    COMPLETED,

    /**
     * Order is cancelled
     */
    CANCELLED,

    /**
     * Order is returned
     */
    RETURNED
}
