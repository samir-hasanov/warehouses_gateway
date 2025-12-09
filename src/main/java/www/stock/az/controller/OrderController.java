package www.stock.az.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.stock.az.dto.request.warehousesorder.OrderCreateRequest;
import www.stock.az.dto.request.warehousesorder.OrderUpdateRequest;
import www.stock.az.dto.response.warehousesorder.OrderResponse;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;
import www.stock.az.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/1.1/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Orders", description = "Order management API endpoints")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "Create a new order", description = "Creates a new order with items and applies discounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        try {
            OrderResponse response = orderService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Returns an order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id) {
        try {
            OrderResponse order = orderService.findById(id);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Get order by order number", description = "Returns an order by its order number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponse> getOrderByNumber(
            @Parameter(description = "Order number", required = true) @PathVariable String orderNumber) {
        try {
            OrderResponse order = orderService.findByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all orders", description = "Returns a list of all orders")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        try {
            List<OrderResponse> orders = orderService.findAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status", description = "Returns orders filtered by order status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Order status", required = true) @PathVariable OrderStatus status) {
        try {
            List<OrderResponse> orders = orderService.findByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update order", description = "Updates an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<OrderResponse> updateOrder(
            @Parameter(description = "Order ID", required = true) @PathVariable Long id,
            @Valid @RequestBody OrderUpdateRequest request) {
        try {
            OrderResponse response = orderService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
