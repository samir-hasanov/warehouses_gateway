package www.stock.az.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import www.stock.az.dto.request.warehousesorder.DiscountCreateRequest;
import www.stock.az.dto.request.warehousesorder.DiscountUpdateRequest;
import www.stock.az.dto.request.warehousesorder.OrderCreateRequest;
import www.stock.az.dto.request.warehousesorder.OrderUpdateRequest;
import www.stock.az.dto.request.warehousesorder.PriceCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceUpdateRequest;
import www.stock.az.dto.response.warehousesorder.DiscountResponse;
import www.stock.az.dto.response.warehousesorder.OrderResponse;
import www.stock.az.dto.response.warehousesorder.PriceResponse;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;
import www.stock.az.properties.WarehousesOrderApi;
import www.stock.az.properties.WarehousesOrderClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class WebClientWarehousesOrder {
    private final WebClient webClient;
    private final WarehousesOrderApi warehousesOrderApi;

    public WebClientWarehousesOrder(WebClient.Builder webClient, WarehousesOrderClient warehousesOrderClient, WarehousesOrderApi warehousesOrderApi) {
        this.webClient = webClient.baseUrl(warehousesOrderClient.getBaseUrl()).build();
        this.warehousesOrderApi = warehousesOrderApi;
    }

    // Order methods
    public List<OrderResponse> findAllOrders() {
        try {
            return webClient.get()
                    .uri(warehousesOrderApi.getOrderController_getAllOrders())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling order service: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching orders from order service", e);
            throw new RuntimeException("Failed to fetch orders: " + e.getMessage(), e);
        }
    }

    public OrderResponse findOrderById(Long id) {
        try {
            String uri = warehousesOrderApi.getOrderController_getOrderById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching order by ID {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching order by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch order: " + e.getMessage(), e);
        }
    }

    public OrderResponse findOrderByNumber(String orderNumber) {
        try {
            String uri = warehousesOrderApi.getOrderController_getOrderByNumber()
                    .replace("{orderNumber}", orderNumber);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching order by number {}: Status code {}", orderNumber, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching order by number: {}", orderNumber, e);
            throw new RuntimeException("Failed to fetch order: " + e.getMessage(), e);
        }
    }

    public List<OrderResponse> findOrdersByStatus(OrderStatus status) {
        try {
            String uri = warehousesOrderApi.getOrderController_getOrdersByStatus()
                    .replace("{status}", status.name());
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status1 -> status1.is4xxClientError() || status1.is5xxServerError(),
                            response -> {
                                log.error("Error fetching orders by status {}: Status code {}", status, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching orders by status: {}", status, e);
            throw new RuntimeException("Failed to fetch orders: " + e.getMessage(), e);
        }
    }

    public OrderResponse createOrder(OrderCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesOrderApi.getOrderController_createOrder())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating order: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new RuntimeException("Failed to create order: " + e.getMessage(), e);
        }
    }

    public OrderResponse updateOrder(Long id, OrderUpdateRequest request) {
        try {
            String uri = warehousesOrderApi.getOrderController_updateOrder()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating order {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating order: {}", id, e);
            throw new RuntimeException("Failed to update order: " + e.getMessage(), e);
        }
    }

    // Discount methods
    public List<DiscountResponse> findAllDiscounts() {
        try {
            return webClient.get()
                    .uri(warehousesOrderApi.getDiscountController_getAllDiscounts())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling order service for discounts: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<DiscountResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching discounts from order service", e);
            throw new RuntimeException("Failed to fetch discounts: " + e.getMessage(), e);
        }
    }

    public DiscountResponse findDiscountById(Long id) {
        try {
            String uri = warehousesOrderApi.getDiscountController_getDiscountById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching discount by ID {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(DiscountResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching discount by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch discount: " + e.getMessage(), e);
        }
    }

    public DiscountResponse findDiscountByCode(String code) {
        try {
            String uri = warehousesOrderApi.getDiscountController_getDiscountByCode()
                    .replace("{code}", code);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching discount by code {}: Status code {}", code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(DiscountResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching discount by code: {}", code, e);
            throw new RuntimeException("Failed to fetch discount: " + e.getMessage(), e);
        }
    }

    public List<DiscountResponse> findActiveDiscounts() {
        try {
            return webClient.get()
                    .uri(warehousesOrderApi.getDiscountController_getActiveDiscounts())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching active discounts: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<DiscountResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching active discounts", e);
            throw new RuntimeException("Failed to fetch active discounts: " + e.getMessage(), e);
        }
    }

    public DiscountResponse createDiscount(DiscountCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesOrderApi.getDiscountController_createDiscount())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating discount: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(DiscountResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating discount", e);
            throw new RuntimeException("Failed to create discount: " + e.getMessage(), e);
        }
    }

    public DiscountResponse validateDiscount(String code, BigDecimal orderAmount) {
        try {
            String uri = warehousesOrderApi.getDiscountController_validateDiscount() + "?code=" + code + "&orderAmount=" + orderAmount;
            return webClient.post()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error validating discount {}: Status code {}", code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(DiscountResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error validating discount: {}", code, e);
            return null; // Return null if discount is not valid
        }
    }

    public List<OrderResponse> findByPaymentStatus(PaymentStatus paymentStatus) {
        try {
            String uri = warehousesOrderApi.getOrderController_getOrdersByPaymentStatus()
                    .replace("{paymentStatus}", paymentStatus.name());
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching orders by payment status {}: Status code {}", paymentStatus, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching orders by payment status: {}", paymentStatus, e);
            throw new RuntimeException("Failed to fetch orders: " + e.getMessage(), e);
        }
    }

    public List<OrderResponse> findByCustomerEmail(String email) {
        try {
            String uri = warehousesOrderApi.getOrderController_getOrdersByCustomer()
                    .replace("{email}", email);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching orders by customer {}: Status code {}", email, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching orders by customer: {}", email, e);
            throw new RuntimeException("Failed to fetch orders: " + e.getMessage(), e);
        }
    }

    public List<OrderResponse> searchOrders(String query) {
        try {
            String uri = warehousesOrderApi.getOrderController_searchOrders() + "?q=" + query;
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching orders with query {}: Status code {}", query, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<OrderResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching orders with query: {}", query, e);
            throw new RuntimeException("Failed to search orders: " + e.getMessage(), e);
        }
    }

    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        try {
            String uri = warehousesOrderApi.getOrderController_updateOrderStatus()
                    .replace("{id}", String.valueOf(id)) + "?status=" + status.name();
            return webClient.patch()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status1 -> status1.is4xxClientError() || status1.is5xxServerError(),
                            response -> {
                                log.error("Error updating order status {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating order status: {}", id, e);
            throw new RuntimeException("Failed to update order status: " + e.getMessage(), e);
        }
    }

    public OrderResponse updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        try {
            String uri = warehousesOrderApi.getOrderController_updatePaymentStatus()
                    .replace("{id}", String.valueOf(id)) + "?paymentStatus=" + paymentStatus.name();
            return webClient.patch()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating payment status {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(OrderResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating payment status: {}", id, e);
            throw new RuntimeException("Failed to update payment status: " + e.getMessage(), e);
        }
    }

    public void cancelOrder(Long id, String reason) {
        try {
            String uri = warehousesOrderApi.getOrderController_cancelOrder()
                    .replace("{id}", String.valueOf(id)) + "?reason=" + (reason != null ? reason : "");
            webClient.post()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error cancelling order {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error cancelling order: {}", id, e);
            throw new RuntimeException("Failed to cancel order: " + e.getMessage(), e);
        }
    }

    public void completeOrder(Long id) {
        try {
            String uri = warehousesOrderApi.getOrderController_completeOrder()
                    .replace("{id}", String.valueOf(id));
            webClient.post()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error completing order {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error completing order: {}", id, e);
            throw new RuntimeException("Failed to complete order: " + e.getMessage(), e);
        }
    }

    public List<DiscountResponse> searchDiscounts(String query) {
        try {
            String uri = warehousesOrderApi.getDiscountController_searchDiscounts() + "?q=" + query;
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching discounts with query {}: Status code {}", query, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<DiscountResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching discounts with query: {}", query, e);
            throw new RuntimeException("Failed to search discounts: " + e.getMessage(), e);
        }
    }

    public DiscountResponse updateDiscount(Long id, DiscountUpdateRequest request) {
        try {
            String uri = warehousesOrderApi.getDiscountController_updateDiscount()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating discount {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(DiscountResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating discount: {}", id, e);
            throw new RuntimeException("Failed to update discount: " + e.getMessage(), e);
        }
    }

    public void deleteDiscount(Long id) {
        try {
            String uri = warehousesOrderApi.getDiscountController_deleteDiscount()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting discount {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting discount: {}", id, e);
            throw new RuntimeException("Failed to delete discount: " + e.getMessage(), e);
        }
    }

    public PriceResponse findPriceById(Long id) {
        try {
            String uri = warehousesOrderApi.getPriceController_getPriceById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching price by ID {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(PriceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching price by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch price: " + e.getMessage(), e);
        }
    }

    public List<PriceResponse> findByWarehouseId(Long warehouseId) {
        try {
            String uri = warehousesOrderApi.getPriceController_getPricesByWarehouse()
                    .replace("{warehouseId}", String.valueOf(warehouseId));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching prices by warehouse {}: Status code {}", warehouseId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<PriceResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching prices by warehouse: {}", warehouseId, e);
            throw new RuntimeException("Failed to fetch prices: " + e.getMessage(), e);
        }
    }

    public List<PriceResponse> getPriceHistory(Long productId, Long warehouseId) {
        try {
            String uri = warehousesOrderApi.getPriceController_getPriceHistory()
                    .replace("{productId}", String.valueOf(productId));
            if (warehouseId != null) {
                uri += "?warehouseId=" + warehouseId;
            }
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching price history for product {}: Status code {}", productId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<PriceResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching price history for product: {}", productId, e);
            throw new RuntimeException("Failed to fetch price history: " + e.getMessage(), e);
        }
    }

    public PriceResponse updatePrice(Long id, PriceUpdateRequest request) {
        try {
            String uri = warehousesOrderApi.getPriceController_updatePrice()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating price {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(PriceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating price: {}", id, e);
            throw new RuntimeException("Failed to update price: " + e.getMessage(), e);
        }
    }

    public void deletePrice(Long id) {
        try {
            String uri = warehousesOrderApi.getPriceController_deletePrice()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting price {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting price: {}", id, e);
            throw new RuntimeException("Failed to delete price: " + e.getMessage(), e);
        }
    }

    // Price methods
    public PriceResponse getCurrentPrice(Long productId, Long warehouseId, String priceType) {
        try {
            String uri = warehousesOrderApi.getPriceController_getCurrentPrice()
                    .replace("{productId}", String.valueOf(productId))
                    + "?priceType=" + priceType;
            if (warehouseId != null) {
                uri += "&warehouseId=" + warehouseId;
            }
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching current price for product {}: Status code {}", productId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(PriceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching current price for product: {}", productId, e);
            throw new RuntimeException("Failed to fetch current price: " + e.getMessage(), e);
        }
    }

    public List<PriceResponse> getPricesByProduct(Long productId) {
        try {
            String uri = warehousesOrderApi.getPriceController_getPricesByProduct()
                    .replace("{productId}", String.valueOf(productId));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching prices for product {}: Status code {}", productId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<PriceResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching prices for product: {}", productId, e);
            throw new RuntimeException("Failed to fetch prices: " + e.getMessage(), e);
        }
    }

    public PriceResponse createPrice(PriceCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesOrderApi.getPriceController_createPrice())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating price: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(PriceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating price", e);
            throw new RuntimeException("Failed to create price: " + e.getMessage(), e);
        }
    }
}
