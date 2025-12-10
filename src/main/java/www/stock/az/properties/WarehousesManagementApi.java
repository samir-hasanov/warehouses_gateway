package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses-management.api")
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
    
    // Warehouse endpoints
    private String WarehouseController_getAllActiveWarehouses;
    private String WarehouseController_getWarehouseById;
    private String WarehouseController_getWarehouseByCode;
    private String WarehouseController_createWarehouse;
    private String WarehouseController_updateWarehouse;
    private String WarehouseController_deleteWarehouse;
    
    // Product endpoints
    private String ProductController_getAllActiveProducts;
    private String ProductController_getProductById;
    private String ProductController_getProductByCode;
    private String ProductController_getProductByBarcode;
    private String ProductController_searchProducts;
    private String ProductController_createProduct;
    private String ProductController_updateProduct;
    private String ProductController_deleteProduct;
    
    // Stock Movement endpoints
    private String StockMovementController_addStockByBarcode;
    
    // Stock endpoints
    private String StockController_getAllStocks;
    private String StockController_getStockById;
    private String StockController_getStocksByWarehouse;
    private String StockController_getStocksByProduct;
    private String StockController_getLowStockItems;
}
