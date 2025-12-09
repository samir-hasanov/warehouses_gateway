package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.ProductCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.ProductUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.ProductResponse;
import www.stock.az.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<ProductResponse> findAllActive() {
        return webClientWarehousesManagement.findAllActiveProducts();
    }

    @Override
    public ProductResponse findById(Long id) {
        return webClientWarehousesManagement.findProductById(id);
    }

    @Override
    public ProductResponse findByCode(String code) {
        return webClientWarehousesManagement.findProductByCode(code);
    }

    @Override
    public ProductResponse findByBarcode(String barcode) {
        return webClientWarehousesManagement.findProductByBarcode(barcode);
    }

    @Override
    public List<ProductResponse> search(String q) {
        return webClientWarehousesManagement.searchProducts(q);
    }

    @Override
    public ProductResponse create(ProductCreateRequest request) {
        return webClientWarehousesManagement.createProduct(request);
    }

    @Override
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        return webClientWarehousesManagement.updateProduct(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesManagement.deleteProduct(id);
    }
}

