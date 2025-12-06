package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses_management.api")
public class WarehousesManagementApi {
    // Brand endpoints
    private String BrandController_getAllActiveBrands;
    private String BrandController_getBrandById;
    private String BrandController_getBrandByCode;
    private String BrandController_searchBrands;
    private String BrandController_createBrand;
    private String BrandController_updateBrand;
    private String BrandController_deleteBrand;
    
    // Category endpoints
    private String CategoryController_getAllActiveCategories;
    private String CategoryController_getCategoryById;
    private String CategoryController_getCategoryByCode;
    private String CategoryController_searchCategories;
    private String CategoryController_createCategory;
    private String CategoryController_updateCategory;
    private String CategoryController_deleteCategory;
}
