package www.stock.az.dto.response.warehousesorder;

import lombok.Data;
import www.stock.az.enums.DiscountType;

import java.math.BigDecimal;

@Data
public class OrderDiscountResponse {
    
    private Long id;
    
    private Long discountId;
    
    private DiscountType discountType;
    
    private String discountName;
    
    private BigDecimal discountValue;
    
    private BigDecimal discountAmount;
    
    private String promoCode;
    
    private String notes;
}
