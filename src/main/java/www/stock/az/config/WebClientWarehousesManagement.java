package www.stock.az.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import www.stock.az.dto.request.warehousesmanagement.BrandCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.BrandUpdateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryUpdateRequest;
import www.stock.az.dto.request.warehousesmanagement.InvoiceCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.ProductCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.ProductUpdateRequest;
import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.request.warehousesmanagement.StockMovementCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.WarehouseCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.WarehouseUpdateRequest;
import www.stock.az.dto.response.PageResponse;
import www.stock.az.dto.response.warehousesmanagement.BrandResponse;
import www.stock.az.dto.response.warehousesmanagement.CategoryResponse;
import www.stock.az.dto.response.warehousesmanagement.InvoiceResponse;
import www.stock.az.dto.response.warehousesmanagement.ProductResponse;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;
import www.stock.az.dto.response.warehousesmanagement.StockResponse;
import www.stock.az.dto.response.warehousesmanagement.WarehouseResponse;
import www.stock.az.properties.WarehousesManagementApi;
import www.stock.az.properties.WarehousesManagementClient;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WebClientWarehousesManagement {
    private final WebClient webClient;
    private final WarehousesManagementApi warehousesManagementApi;


    public WebClientWarehousesManagement(WebClient.Builder webClient, WarehousesManagementClient warehousesManagementClient, WarehousesManagementApi warehousesManagementApi) {
        this.webClient = webClient
                .baseUrl(warehousesManagementClient.getBaseUrl())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // 2 MB
                .build();
        this.warehousesManagementApi = warehousesManagementApi;
    }

    // Brand methods
    public List<BrandResponse> findAllActiveBrands() {
        try {
            return webClient.get()
                    .uri(warehousesManagementApi.getBrandController_getAllActiveBrands())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling warehouses management service: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<BrandResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching active brands from warehouses management service", e);
            throw new RuntimeException("Failed to fetch brands: " + e.getMessage(), e);
        }
    }

    public BrandResponse findBrandById(Long id) {
        try {
            String uri = warehousesManagementApi.getBrandController_getBrandById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching brand by ID {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(BrandResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching brand by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch brand: " + e.getMessage(), e);
        }
    }

    public BrandResponse findBrandByCode(String code) {
        try {
            String uri = warehousesManagementApi.getBrandController_getBrandByCode()
                    .replace("{code}", code);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching brand by code {}: Status code {}", 
                                        code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(BrandResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching brand by code: {}", code, e);
            throw new RuntimeException("Failed to fetch brand: " + e.getMessage(), e);
        }
    }

    public List<BrandResponse> searchBrands(String query) {
        try {
            String uri = warehousesManagementApi.getBrandController_searchBrands() + "?q=" + query;
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching brands with query {}: Status code {}", 
                                        query, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<BrandResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching brands with query: {}", query, e);
            throw new RuntimeException("Failed to search brands: " + e.getMessage(), e);
        }
    }

    public BrandResponse createBrand(BrandCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getBrandController_createBrand())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating brand: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(BrandResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating brand", e);
            throw new RuntimeException("Failed to create brand: " + e.getMessage(), e);
        }
    }

    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        try {
            String uri = warehousesManagementApi.getBrandController_updateBrand()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating brand {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(BrandResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating brand: {}", id, e);
            throw new RuntimeException("Failed to update brand: " + e.getMessage(), e);
        }
    }

    public void deleteBrand(Long id) {
        try {
            String uri = warehousesManagementApi.getBrandController_deleteBrand()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting brand {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting brand: {}", id, e);
            throw new RuntimeException("Failed to delete brand: " + e.getMessage(), e);
        }
    }

    // Category methods
    public List<CategoryResponse> findAllActiveCategories() {
        try {
            return webClient.get()
                    .uri(warehousesManagementApi.getCategoryController_getAllActiveCategories())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling warehouses management service: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<CategoryResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching active categories from warehouses management service", e);
            throw new RuntimeException("Failed to fetch categories: " + e.getMessage(), e);
        }
    }

    public CategoryResponse findCategoryById(Long id) {
        try {
            String uri = warehousesManagementApi.getCategoryController_getCategoryById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching category by ID {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(CategoryResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching category by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch category: " + e.getMessage(), e);
        }
    }

    public CategoryResponse findCategoryByCode(String code) {
        try {
            String uri = warehousesManagementApi.getCategoryController_getCategoryByCode()
                    .replace("{code}", code);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching category by code {}: Status code {}", 
                                        code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(CategoryResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching category by code: {}", code, e);
            throw new RuntimeException("Failed to fetch category: " + e.getMessage(), e);
        }
    }

    public List<CategoryResponse> searchCategories(String query) {
        try {
            String uri = warehousesManagementApi.getCategoryController_searchCategories() + "?q=" + query;
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching categories with query {}: Status code {}", 
                                        query, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<CategoryResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching categories with query: {}", query, e);
            throw new RuntimeException("Failed to search categories: " + e.getMessage(), e);
        }
    }

    public CategoryResponse createCategory(CategoryCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getCategoryController_createCategory())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating category: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(CategoryResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating category", e);
            throw new RuntimeException("Failed to create category: " + e.getMessage(), e);
        }
    }

    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        try {
            String uri = warehousesManagementApi.getCategoryController_updateCategory()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating category {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(CategoryResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating category: {}", id, e);
            throw new RuntimeException("Failed to update category: " + e.getMessage(), e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            String uri = warehousesManagementApi.getCategoryController_deleteCategory()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting category {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting category: {}", id, e);
            throw new RuntimeException("Failed to delete category: " + e.getMessage(), e);
        }
    }

    // Warehouse methods
    public List<WarehouseResponse> findAllActiveWarehouses() {
        try {
            return webClient.get()
                    .uri(warehousesManagementApi.getWarehouseController_getAllActiveWarehouses())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling warehouses management service: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<WarehouseResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching active warehouses from warehouses management service", e);
            throw new RuntimeException("Failed to fetch warehouses: " + e.getMessage(), e);
        }
    }

    public WarehouseResponse findWarehouseById(Long id) {
        try {
            String uri = warehousesManagementApi.getWarehouseController_getWarehouseById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching warehouse by ID {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(WarehouseResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching warehouse by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch warehouse: " + e.getMessage(), e);
        }
    }

    public WarehouseResponse findWarehouseByCode(String code) {
        try {
            String uri = warehousesManagementApi.getWarehouseController_getWarehouseByCode()
                    .replace("{code}", code);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching warehouse by code {}: Status code {}", 
                                        code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(WarehouseResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching warehouse by code: {}", code, e);
            throw new RuntimeException("Failed to fetch warehouse: " + e.getMessage(), e);
        }
    }

    public WarehouseResponse createWarehouse(WarehouseCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getWarehouseController_createWarehouse())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating warehouse: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(WarehouseResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
            log.error("Error creating warehouse: Status {}, Body: {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            String errorMessage = "Anbar yaradılarkən xəta baş verdi";
            if (e.getResponseBodyAsString() != null && !e.getResponseBodyAsString().isEmpty()) {
                try {
                    // Try to extract message from response
                    errorMessage = e.getResponseBodyAsString();
                } catch (Exception ex) {
                    log.warn("Could not parse error response", ex);
                }
            }
            throw new RuntimeException(errorMessage, e);
        } catch (Exception e) {
            log.error("Error creating warehouse", e);
            throw new RuntimeException("Anbar yaradılarkən xəta baş verdi: " + e.getMessage(), e);
        }
    }

    public WarehouseResponse updateWarehouse(Long id, WarehouseUpdateRequest request) {
        try {
            String uri = warehousesManagementApi.getWarehouseController_updateWarehouse()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating warehouse {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(WarehouseResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException e) {
            log.error("Error updating warehouse {}: Status {}, Body: {}", id, e.getStatusCode(), e.getResponseBodyAsString(), e);
            String errorMessage = "Anbar yenilənərkən xəta baş verdi";
            if (e.getResponseBodyAsString() != null && !e.getResponseBodyAsString().isEmpty()) {
                try {
                    errorMessage = e.getResponseBodyAsString();
                } catch (Exception ex) {
                    log.warn("Could not parse error response", ex);
                }
            }
            throw new RuntimeException(errorMessage, e);
        } catch (Exception e) {
            log.error("Error updating warehouse: {}", id, e);
            throw new RuntimeException("Anbar yenilənərkən xəta baş verdi: " + e.getMessage(), e);
        }
    }

    public void deleteWarehouse(Long id) {
        try {
            String uri = warehousesManagementApi.getWarehouseController_deleteWarehouse()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting warehouse {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting warehouse: {}", id, e);
            throw new RuntimeException("Failed to delete warehouse: " + e.getMessage(), e);
        }
    }

    // Product methods
    public PageResponse<ProductResponse> findAllActiveProducts(int page, int size) {
        String uri = warehousesManagementApi.getProductController_getAllActiveProducts();
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(uri)
                            .queryParam("page", page)
                            .queryParam("size", size)
                            .build()
                    )
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.createException()
                    )
                    .bodyToMono(new ParameterizedTypeReference<
                            PageResponse<ProductResponse>>() {})
                    .block();

        } catch (Exception e) {
            log.error("Gateway product pagination error", e);
            throw new RuntimeException("Product service çağırıla bilmədi", e);
        }
    }

    public ProductResponse findProductById(Long id) {
        try {
            String uri = warehousesManagementApi.getProductController_getProductById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching product by ID {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(ProductResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching product by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch product: " + e.getMessage(), e);
        }
    }

    public ProductResponse findProductByCode(String code) {
        try {
            String uri = warehousesManagementApi.getProductController_getProductByCode()
                    .replace("{code}", code);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching product by code {}: Status code {}", 
                                        code, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(ProductResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching product by code: {}", code, e);
            throw new RuntimeException("Failed to fetch product: " + e.getMessage(), e);
        }
    }

    public ProductResponse findProductByBarcode(String barcode) {
        try {
            String uri = warehousesManagementApi.getProductController_getProductByBarcode()
                    .replace("{barcode}", barcode);
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching product by barcode {}: Status code {}", 
                                        barcode, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(ProductResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching product by barcode: {}", barcode, e);
            throw new RuntimeException("Failed to fetch product: " + e.getMessage(), e);
        }
    }

    public List<ProductResponse> searchProducts(String query) {
        try {
            String uri = warehousesManagementApi.getProductController_searchProducts() + "?q=" + query;
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching products with query {}: Status code {}", 
                                        query, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<ProductResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching products with query: {}", query, e);
            throw new RuntimeException("Failed to search products: " + e.getMessage(), e);
        }
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getProductController_createProduct())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating product: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(ProductResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating product", e);
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    public ProductResponse updateProduct(Long id, ProductUpdateRequest request) {
        try {
            String uri = warehousesManagementApi.getProductController_updateProduct()
                    .replace("{id}", String.valueOf(id));
            return webClient.put()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error updating product {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(ProductResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error updating product: {}", id, e);
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    public void deleteProduct(Long id) {
        try {
            String uri = warehousesManagementApi.getProductController_deleteProduct()
                    .replace("{id}", String.valueOf(id));
            webClient.delete()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error deleting product {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(Void.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error deleting product: {}", id, e);
            throw new RuntimeException("Failed to delete product: " + e.getMessage(), e);
        }
    }

    // Stock Movement methods
    public List<StockMovementResponse> getAllStockMovements(String type) {
        try {
            String uri = warehousesManagementApi.getStockMovementController_getAllStockMovements();
            if (type != null && !type.isEmpty()) {
                uri += "?type=" + type;
            }
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error getting stock movements: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<StockMovementResponse>>() {})
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error getting stock movements", e);
            throw new RuntimeException("Failed to get stock movements: " + e.getMessage(), e);
        }
    }
    
    public StockMovementResponse createStockMovement(StockMovementCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getStockMovementController_createStockMovement())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating stock movement: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(StockMovementResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating stock movement", e);
            throw new RuntimeException("Failed to create stock movement: " + e.getMessage(), e);
        }
    }
    
    public StockMovementResponse addStockByBarcode(StockInRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getStockMovementController_addStockByBarcode())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error adding stock by barcode: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(StockMovementResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error adding stock by barcode", e);
            throw new RuntimeException("Failed to add stock by barcode: " + e.getMessage(), e);
        }
    }
    
    public StockMovementResponse approveStockMovement(Long id, String approvedBy) {
        try {
            String uri = warehousesManagementApi.getStockMovementController_approveStockMovement()
                    .replace("{id}", String.valueOf(id));
            Map<String, String> body = new HashMap<>();
            body.put("approvedBy", approvedBy);
            return webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error approving stock movement: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(StockMovementResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error approving stock movement", e);
            throw new RuntimeException("Failed to approve stock movement: " + e.getMessage(), e);
        }
    }
    
    public StockMovementResponse cancelStockMovement(Long id, String reason) {
        try {
            String uri = warehousesManagementApi.getStockMovementController_cancelStockMovement()
                    .replace("{id}", String.valueOf(id));
            Map<String, String> body = new HashMap<>();
            body.put("reason", reason);
            return webClient.post()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error cancelling stock movement: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(StockMovementResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error cancelling stock movement", e);
            throw new RuntimeException("Failed to cancel stock movement: " + e.getMessage(), e);
        }
    }

    // Stock methods
    public List<StockResponse> findAllStocks() {
        try {
            return webClient.get()
                    .uri(warehousesManagementApi.getStockController_getAllStocks())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error calling warehouses management service: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<StockResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching stocks from warehouses management service", e);
            throw new RuntimeException("Failed to fetch stocks: " + e.getMessage(), e);
        }
    }

    public StockResponse findStockById(Long id) {
        try {
            String uri = warehousesManagementApi.getStockController_getStockById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching stock by ID {}: Status code {}", 
                                        id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(StockResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching stock by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch stock: " + e.getMessage(), e);
        }
    }

    public List<StockResponse> findStocksByWarehouse(Long warehouseId) {
        try {
            String uri = warehousesManagementApi.getStockController_getStocksByWarehouse()
                    .replace("{warehouseId}", String.valueOf(warehouseId));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching stocks by warehouse {}: Status code {}", 
                                        warehouseId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<StockResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching stocks by warehouse: {}", warehouseId, e);
            throw new RuntimeException("Failed to fetch stocks: " + e.getMessage(), e);
        }
    }

    public List<StockResponse> findStocksByProduct(Long productId) {
        try {
            String uri = warehousesManagementApi.getStockController_getStocksByProduct()
                    .replace("{productId}", String.valueOf(productId));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching stocks by product {}: Status code {}", 
                                        productId, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<StockResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching stocks by product: {}", productId, e);
            throw new RuntimeException("Failed to fetch stocks: " + e.getMessage(), e);
        }
    }

    public List<StockResponse> findLowStockItems(Long warehouseId) {
        try {
            String uri = warehousesManagementApi.getStockController_getLowStockItems();
            if (warehouseId != null) {
                uri += "?warehouseId=" + warehouseId;
            }
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching low stock items: Status code {}", 
                                        response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<StockResponse>>() {
                    })
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching low stock items", e);
            throw new RuntimeException("Failed to fetch low stock items: " + e.getMessage(), e);
        }
    }

    // Invoice methods
    public InvoiceResponse createInvoice(InvoiceCreateRequest request) {
        try {
            return webClient.post()
                    .uri(warehousesManagementApi.getInvoiceController_create())
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error creating invoice: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(InvoiceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error creating invoice", e);
            throw new RuntimeException("Failed to create invoice: " + e.getMessage(), e);
        }
    }

    public InvoiceResponse findInvoiceById(Long id) {
        try {
            String uri = warehousesManagementApi.getInvoiceController_getById()
                    .replace("{id}", String.valueOf(id));
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching invoice by ID {}: Status code {}", id, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(InvoiceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching invoice by ID: {}", id, e);
            throw new RuntimeException("Failed to fetch invoice: " + e.getMessage(), e);
        }
    }

    public InvoiceResponse findInvoiceByNumber(String invoiceNumber) {
        try {
            String uri = warehousesManagementApi.getInvoiceController_getByNumber()
                    .replace("{invoiceNumber}", invoiceNumber != null ? invoiceNumber : "");
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error fetching invoice by number {}: Status code {}", invoiceNumber, response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(InvoiceResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error fetching invoice by number: {}", invoiceNumber, e);
            throw new RuntimeException("Failed to fetch invoice: " + e.getMessage(), e);
        }
    }

    public List<InvoiceResponse> searchInvoices(String direction, LocalDateTime fromDate, LocalDateTime toDate) {
        try {
            StringBuilder uri = new StringBuilder(warehousesManagementApi.getInvoiceController_search());
            boolean first = true;
            if (direction != null && !direction.isEmpty()) {
                uri.append(first ? "?" : "&").append("direction=").append(direction);
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
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                log.error("Error searching invoices: Status code {}", response.statusCode());
                                return response.createException();
                            })
                    .bodyToMono(new ParameterizedTypeReference<List<InvoiceResponse>>() {})
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            log.error("Error searching invoices", e);
            throw new RuntimeException("Failed to search invoices: " + e.getMessage(), e);
        }
    }
}
