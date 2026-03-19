package www.stock.az.dto.request.warehousesorder;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductReturnCreateRequest {
    private String returnNumber;
    private Long orderId;
    private Long invoiceId;
    private LocalDateTime returnDate;
    private BigDecimal totalAmount;
    private String notes;
    private String currency;
}
