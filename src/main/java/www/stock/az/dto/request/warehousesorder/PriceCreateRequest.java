package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceCreateRequest {
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    private Long warehouseId; // null means applies to all warehouses
    
    @NotBlank(message = "Price type is required")
    private String priceType; // SELLING, COST, WHOLESALE, RETAIL
    
    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than 0")
    private BigDecimal unitPrice;
    
    private String currency = "AZN";
    
    private BigDecimal minQuantity; // Minimum quantity for this price tier
    
    @NotNull(message = "Valid from date is required")
    private LocalDateTime validFrom;
    
    private LocalDateTime validTo; // null means no expiration
    
    private Boolean isActive = true;
    
    private String notes;
}
