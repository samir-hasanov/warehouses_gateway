package www.stock.az.service;

import www.stock.az.dto.request.warehousesorder.OrderCreateRequest;
import www.stock.az.dto.request.warehousesorder.OrderUpdateRequest;
import www.stock.az.dto.response.warehousesorder.OrderResponse;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderResponse create(OrderCreateRequest request);

    OrderResponse findById(Long id);

    OrderResponse findByOrderNumber(String orderNumber);

    List<OrderResponse> findAll();

    List<OrderResponse> search(OrderStatus status, LocalDateTime fromDate, LocalDateTime toDate);

    List<OrderResponse> findByStatus(OrderStatus status);
    
    List<OrderResponse> findByPaymentStatus(PaymentStatus paymentStatus);
    
    List<OrderResponse> findByCustomerEmail(String email);
    
    List<OrderResponse> search(String query);
    
    OrderResponse update(Long id, OrderUpdateRequest request);
    
    OrderResponse updateStatus(Long id, OrderStatus status);
    
    OrderResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus);
    
    void cancel(Long id, String reason);
    
    void complete(Long id);
}
