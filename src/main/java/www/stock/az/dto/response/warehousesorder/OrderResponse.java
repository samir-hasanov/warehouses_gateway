package www.stock.az.dto.response.warehousesorder;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private Long id;

    private String orderNumber;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime orderDate;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private String deliveryAddress;

    private BigDecimal subtotal;

    private BigDecimal totalDiscount;

    private BigDecimal taxAmount;

    private BigDecimal shippingCost;

    private BigDecimal totalAmount;

    private String currency;

    private String notes;

    private Long warehouseId;

    private LocalDateTime completedAt;

    @JsonAlias("items")
    private List<OrderItemResponse> orderItems;
    
    private List<OrderDiscountResponse> orderDiscounts;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
