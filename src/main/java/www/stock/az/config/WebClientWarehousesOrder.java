package www.stock.az.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import www.stock.az.dto.request.warehousesorder.DiscountCreateRequest;
import www.stock.az.dto.request.warehousesorder.DiscountUpdateRequest;
import www.stock.az.dto.request.warehousesorder.OrderCreateRequest;
import www.stock.az.dto.request.warehousesorder.OrderUpdateRequest;
import www.stock.az.dto.request.warehousesorder.PaymentCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceUpdateRequest;
import www.stock.az.dto.request.warehousesorder.ProductReturnCreateRequest;
import www.stock.az.dto.response.warehousesorder.DiscountResponse;
import www.stock.az.dto.response.warehousesorder.OrderResponse;
import www.stock.az.dto.response.warehousesorder.PaymentResponse;
import www.stock.az.dto.response.warehousesorder.PriceResponse;
import www.stock.az.dto.response.warehousesorder.ProductReturnResponse;
import www.stock.az.enums.OrderStatus;
import www.stock.az.enums.PaymentStatus;
import www.stock.az.enums.PaymentType;
import www.stock.az.enums.ReturnStatus;
import www.stock.az.properties.WarehousesOrderApi;
import www.stock.az.properties.WarehousesOrderClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
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
        return searchOrders(null, null, null);
    }

    public List<OrderResponse> searchOrders(OrderStatus status, LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            StringBuilder uri = new StringBuilder(warehousesOrderApi.getOrderController_getAllOrders());
            boolean first = true;
            if (status != null) {
                uri.append(first ? "?" : "&").append("status=").append(status.name());
                first = false;
            }
            if (fromDate != null) {
                uri.append(first ? "?" : "&").append("fromDate=").append(fromDate.toString());
                first = false;
            }
            if (toDate != null) {
                uri.append(first ? "?" : "&").append("toDate=").append(toDate.toString());
            }
            return webClient.get()
                    .uri(uri.toString())
                    .retrieve()
                    .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
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
                            response -> response.bodyToMono(String.class).flatMap(body -> {
                                log.error("Error fetching all discounts: Status code {}, Response body: {}", response.statusCode(), body);
                                return Mono.error(new RuntimeException("Failed to fetch discounts: " + body));
                            }))
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

    public List<PriceResponse> findAllPrices() {
        try {
            return webClient.get()
                    .uri(warehousesOrderApi.getPriceController_getAllPrices())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class).flatMap(body -> {
                                log.error("Error fetching all prices: Status code {}, Response body: {}", response.statusCode(), body);
                                return Mono.error(new RuntimeException("Failed to fetch prices: " + body));
                            }))
                    .bodyToMono(new ParameterizedTypeReference<List<PriceResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching all prices", e);
            throw new RuntimeException("Failed to fetch prices: " + e.getMessage(), e);
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
                            response -> response.bodyToMono(String.class).flatMap(body -> {
                                log.error("Error fetching prices for product {}: Status code {}, Response body: {}", productId, response.statusCode(), body);
                                return Mono.error(new RuntimeException("Failed to fetch prices: " + body));
                            }))
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

    // Payment methods (ödənişlər: kart, nağd, əvəzləşmə)
    public PaymentResponse createPayment(PaymentCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesOrderApi.getPaymentController_create())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating payment", e);
            throw new RuntimeException("Failed to create payment: " + e.getMessage(), e);
        }
    }

    public PaymentResponse findPaymentById(Long id) {
        try {
            String uri = warehousesOrderApi.getPaymentController_getById().replace("{id}", String.valueOf(id));
            return webClient.get().uri(uri).retrieve().bodyToMono(PaymentResponse.class).timeout(Duration.ofSeconds(10)).block();
        } catch (Exception e) {
            log.error("Error fetching payment {}", id, e);
            throw new RuntimeException("Failed to fetch payment: " + e.getMessage(), e);
        }
    }

    public List<PaymentResponse> searchPayments(PaymentType paymentType, LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            StringBuilder uri = new StringBuilder(warehousesOrderApi.getPaymentController_search());
            boolean first = true;
            if (paymentType != null) { uri.append(first ? "?" : "&").append("paymentType=").append(paymentType.name()); first = false; }
            if (fromDate != null) { uri.append(first ? "?" : "&").append("fromDate=").append(fromDate.toString()); first = false; }
            if (toDate != null) { uri.append(first ? "?" : "&").append("toDate=").append(toDate.toString()); }
            return webClient.get().uri(uri.toString()).retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PaymentResponse>>() {}).timeout(Duration.ofSeconds(10)).block();
        } catch (Exception e) {
            log.error("Error searching payments", e);
            throw new RuntimeException("Failed to search payments: " + e.getMessage(), e);
        }
    }

    // Return methods (geri qaytarma)
    public ProductReturnResponse createReturn(ProductReturnCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesOrderApi.getProductReturnController_create())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ProductReturnResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating return", e);
            throw new RuntimeException("Failed to create return: " + e.getMessage(), e);
        }
    }

    public ProductReturnResponse findReturnById(Long id) {
        try {
            String uri = warehousesOrderApi.getProductReturnController_getById().replace("{id}", String.valueOf(id));
            return webClient.get().uri(uri).retrieve().bodyToMono(ProductReturnResponse.class).timeout(Duration.ofSeconds(10)).block();
        } catch (Exception e) {
            log.error("Error fetching return {}", id, e);
            throw new RuntimeException("Failed to fetch return: " + e.getMessage(), e);
        }
    }

    public List<ProductReturnResponse> searchReturns(ReturnStatus status, LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            StringBuilder uri = new StringBuilder(warehousesOrderApi.getProductReturnController_search());
            boolean first = true;
            if (status != null) { uri.append(first ? "?" : "&").append("status=").append(status.name()); first = false; }
            if (fromDate != null) { uri.append(first ? "?" : "&").append("fromDate=").append(fromDate.toString()); first = false; }
            if (toDate != null) { uri.append(first ? "?" : "&").append("toDate=").append(toDate.toString()); }
            return webClient.get().uri(uri.toString()).retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ProductReturnResponse>>() {}).timeout(Duration.ofSeconds(10)).block();
        } catch (Exception e) {
            log.error("Error searching returns", e);
            throw new RuntimeException("Failed to search returns: " + e.getMessage(), e);
        }
    }
}
