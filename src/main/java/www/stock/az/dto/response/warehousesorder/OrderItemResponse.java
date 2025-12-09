package www.stock.az.dto.response.warehousesorder;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    
    private Long id;
    
    private Long productId;
    
    private String productCode;
    
    private String productName;
    
    private BigDecimal quantity;
    
    private BigDecimal unitPrice;
    
    private BigDecimal discountAmount;
    
    private BigDecimal discountPercentage;
    
    private BigDecimal subtotal;
    
    private BigDecimal total;
    
    private BigDecimal taxAmount;
    
    private String notes;
}
