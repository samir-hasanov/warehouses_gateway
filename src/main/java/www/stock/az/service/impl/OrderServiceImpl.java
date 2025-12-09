package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesOrder;
import www.stock.az.dto.request.warehousesorder.OrderCreateRequest;
import www.stock.az.dto.request.warehousesorder.OrderUpdateRequest;
import www.stock.az.dto.response.warehousesorder.OrderResponse;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;
import www.stock.az.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final WebClientWarehousesOrder webClientWarehousesOrder;

    @Override
    public OrderResponse create(OrderCreateRequest request) {
        return webClientWarehousesOrder.createOrder(request);
    }

    @Override
    public OrderResponse findById(Long id) {
        return webClientWarehousesOrder.findOrderById(id);
    }

    @Override
    public OrderResponse findByOrderNumber(String orderNumber) {
        return webClientWarehousesOrder.findOrderByNumber(orderNumber);
    }

    @Override
    public List<OrderResponse> findAll() {
        return webClientWarehousesOrder.findAllOrders();
    }

    @Override
    public List<OrderResponse> findByStatus(OrderStatus status) {
        return webClientWarehousesOrder.findOrdersByStatus(status);
    }

    @Override
    public List<OrderResponse> findByPaymentStatus(PaymentStatus paymentStatus) {
        return webClientWarehousesOrder.findByPaymentStatus(paymentStatus);
    }

    @Override
    public List<OrderResponse> findByCustomerEmail(String email) {
        return webClientWarehousesOrder.findByCustomerEmail(email);
    }

    @Override
    public List<OrderResponse> search(String query) {
        return webClientWarehousesOrder.searchOrders(query);
    }

    @Override
    public OrderResponse update(Long id, OrderUpdateRequest request) {
        return webClientWarehousesOrder.updateOrder(id, request);
    }

    @Override
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        return webClientWarehousesOrder.updateOrderStatus(id, status);
    }

    @Override
    public OrderResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        return webClientWarehousesOrder.updatePaymentStatus(id, paymentStatus);
    }

    @Override
    public void cancel(Long id, String reason) {
        webClientWarehousesOrder.cancelOrder(id, reason);
    }

    @Override
    public void complete(Long id) {
        webClientWarehousesOrder.completeOrder(id);
    }
}
