package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import www.stock.az.enums.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DiscountCreateRequest {
    
    @NotBlank(message = "Discount code is required")
    private String code;
    
    @NotBlank(message = "Discount name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Discount type is required")
    private DiscountType discountType;
    
    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.01", message = "Discount value must be greater than 0")
    private BigDecimal discountValue;
    
    private BigDecimal minPurchaseAmount;
    
    private BigDecimal maxDiscountAmount;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer usageLimit;
    
    private Boolean isActive = true;
    
    private Boolean applicableToAllProducts = true;
    
    private String productIds; // Comma-separated product IDs
    
    private String customerIds; // Comma-separated customer IDs
}
