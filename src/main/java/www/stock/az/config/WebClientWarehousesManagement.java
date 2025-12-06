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
        return webClient.get()
                .uri(warehousesManagementApi.getBrandController_getAllActiveBrands())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BrandResponse>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public BrandResponse findBrandById(Long id) {
        String uri = warehousesManagementApi.getBrandController_getBrandById()
                .replace("{id}", String.valueOf(id));
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BrandResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public BrandResponse findBrandByCode(String code) {
        String uri = warehousesManagementApi.getBrandController_getBrandByCode()
                .replace("{code}", code);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(BrandResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public List<BrandResponse> searchBrands(String query) {
        String uri = warehousesManagementApi.getBrandController_searchBrands() + "?q=" + query;
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BrandResponse>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public BrandResponse createBrand(BrandCreateRequest request) {
        return webClient.post()
                .uri(warehousesManagementApi.getBrandController_createBrand())
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BrandResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        String uri = warehousesManagementApi.getBrandController_updateBrand()
                .replace("{id}", String.valueOf(id));
        return webClient.put()
                .uri(uri)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BrandResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public void deleteBrand(Long id) {
        String uri = warehousesManagementApi.getBrandController_deleteBrand()
                .replace("{id}", String.valueOf(id));
        webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    // Category methods
    public List<CategoryResponse> findAllActiveCategories() {
        return webClient.get()
                .uri(warehousesManagementApi.getCategoryController_getAllActiveCategories())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryResponse>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public CategoryResponse findCategoryById(Long id) {
        String uri = warehousesManagementApi.getCategoryController_getCategoryById()
                .replace("{id}", String.valueOf(id));
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public CategoryResponse findCategoryByCode(String code) {
        String uri = warehousesManagementApi.getCategoryController_getCategoryByCode()
                .replace("{code}", code);
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public List<CategoryResponse> searchCategories(String query) {
        String uri = warehousesManagementApi.getCategoryController_searchCategories() + "?q=" + query;
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryResponse>>() {
                })
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public CategoryResponse createCategory(CategoryCreateRequest request) {
        return webClient.post()
                .uri(warehousesManagementApi.getCategoryController_createCategory())
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest request) {
        String uri = warehousesManagementApi.getCategoryController_updateCategory()
                .replace("{id}", String.valueOf(id));
        return webClient.put()
                .uri(uri)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }

    public void deleteCategory(Long id) {
        String uri = warehousesManagementApi.getCategoryController_deleteCategory()
                .replace("{id}", String.valueOf(id));
        webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(10))
                .block();
    }
}
