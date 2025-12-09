package www.stock.az.dto.request.warehousesorder;

import jakarta.validation.constraints.Email;
import lombok.Data;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;

@Data
public class OrderUpdateRequest {
    
    private OrderStatus orderStatus;
    
    private PaymentStatus paymentStatus;
    
    private String customerName;
    
    @Email(message = "Invalid email format")
    private String customerEmail;
    
    private String customerPhone;
    
    private String deliveryAddress;
    
    private String notes;
}
