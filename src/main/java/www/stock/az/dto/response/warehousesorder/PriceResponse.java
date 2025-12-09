package www.stock.az.dto.response.warehousesorder;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceResponse {
    
    private Long id;
    
    private Long productId;
    
    private Long warehouseId;
    
    private String priceType;
    
    private BigDecimal unitPrice;
    
    private String currency;
    
    private BigDecimal minQuantity;
    
    private LocalDateTime validFrom;
    
    private LocalDateTime validTo;
    
    private Boolean isActive;
    
    private String notes;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
