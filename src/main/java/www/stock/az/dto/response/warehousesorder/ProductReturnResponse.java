package www.stock.az.dto.response.warehousesorder;

import lombok.Data;
import www.stock.az.enums.ReturnStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductReturnResponse {
    private Long id;
    private String returnNumber;
    private Long orderId;
    private Long invoiceId;
    private LocalDateTime returnDate;
    private ReturnStatus status;
    private BigDecimal totalAmount;
    private String notes;
    private String currency;
}
