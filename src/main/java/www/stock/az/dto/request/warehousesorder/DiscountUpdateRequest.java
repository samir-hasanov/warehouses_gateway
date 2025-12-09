package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import www.stock.az.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountUpdateRequest {
    
    private String name;
    
    private String description;
    
    private DiscountType discountType;
    
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxDiscountAmount;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer usageLimit;
    
    private Boolean isActive;
    
    private Boolean applicableToAllProducts;
    
    private String productIds;
    
    private String customerIds;
}
