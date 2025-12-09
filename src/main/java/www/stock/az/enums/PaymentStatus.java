package www.stock.az.enums;

/**
 * Payment status enumeration
 */
public enum PaymentStatus {
    /**
     * Payment is pending
     */
    PENDING,

    /**
     * Payment is partially paid
     */
    PARTIALLY_PAID,

    /**
     * Payment is fully paid
     */
    PAID,

    /**
     * Payment failed
     */
    FAILED,

    /**
     * Payment is refunded
     */
    REFUNDED,

    /**
     * Payment is cancelled
     */
    CANCELLED
}
