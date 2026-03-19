package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateRequest {

    private String orderNumber;

    private java.time.LocalDateTime orderDate;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    private String customerName;

    private String customerEmail;

    private String customerPhone;

    private String deliveryAddress;

    @NotEmpty(message = "Order items are required")
    @Valid
    private List<OrderItemRequest> orderItems;

    private List<String> discountCodes;

    private String currency = "AZN";

    private String notes;
}
