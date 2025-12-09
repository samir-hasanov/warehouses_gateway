package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses-order-service.api")
public class WarehousesOrderApi {
    
    // Order endpoints
    private String OrderController_getAllOrders;
    private String OrderController_getOrderById;
    private String OrderController_getOrderByNumber;
    private String OrderController_getOrdersByStatus;
    private String OrderController_getOrdersByPaymentStatus;
    private String OrderController_getOrdersByCustomer;
    private String OrderController_searchOrders;
    private String OrderController_createOrder;
    private String OrderController_updateOrder;
    private String OrderController_updateOrderStatus;
    private String OrderController_updatePaymentStatus;
    private String OrderController_cancelOrder;
    private String OrderController_completeOrder;
    
    // Discount endpoints
    private String DiscountController_getAllDiscounts;
    private String DiscountController_getDiscountById;
    private String DiscountController_getDiscountByCode;
    private String DiscountController_getActiveDiscounts;
    private String DiscountController_searchDiscounts;
    private String DiscountController_createDiscount;
    private String DiscountController_updateDiscount;
    private String DiscountController_deleteDiscount;
    private String DiscountController_validateDiscount;
    
    // Price endpoints
    private String PriceController_getAllPrices;
    private String PriceController_getPriceById;
    private String PriceController_getPricesByProduct;
    private String PriceController_getPricesByWarehouse;
    private String PriceController_getCurrentPrice;
    private String PriceController_getPriceHistory;
    private String PriceController_createPrice;
    private String PriceController_updatePrice;
    private String PriceController_deletePrice;
}
