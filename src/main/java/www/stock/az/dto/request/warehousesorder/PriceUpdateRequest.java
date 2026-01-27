package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceUpdateRequest {
    
    private Long warehouseId;
    
    private String priceType;
    
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
    
    private String currency;
    
    private BigDecimal minQuantity;
    
    private LocalDateTime validFrom;
    
    private LocalDateTime validTo;
    
    private Boolean isActive;
    
    private String notes;
}
