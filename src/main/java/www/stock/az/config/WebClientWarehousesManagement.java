package www.stock.az.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import www.stock.az.dto.request.warehousesmanagement.BrandCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.BrandUpdateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.BrandResponse;
import www.stock.az.dto.response.warehousesmanagement.CategoryResponse;
import www.stock.az.properties.WarehousesManagementApi;
import www.stock.az.properties.WarehousesManagementClient;

import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class WebClientWarehousesManagement {
    private final WebClient webClient;
    private final WarehousesManagementApi warehousesManagementApi;


    public WebClientWarehousesManagement(WebClient.Builder webClient, WarehousesManagementClient warehousesManagementClient, WarehousesManagementApi warehousesManagementApi) {
        this.webClient = webClient.baseUrl(warehousesManagementClient.getBaseUrl()).build();
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
}
