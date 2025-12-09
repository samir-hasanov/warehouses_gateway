package www.stock.az.service;

import www.stock.az.dto.request.warehousesmanagement.ProductCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.ProductUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAllActive();

    ProductResponse findById(Long id);

    ProductResponse findByCode(String code);

    ProductResponse findByBarcode(String barcode);

    List<ProductResponse> search(String q);

    ProductResponse create(ProductCreateRequest request);

    ProductResponse update(Long id, ProductUpdateRequest request);

    void delete(Long id);
}

