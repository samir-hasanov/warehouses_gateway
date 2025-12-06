package www.stock.az.service;

import www.stock.az.dto.request.warehousesmanagement.CategoryCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAllActive();

    CategoryResponse findById(Long id);

    CategoryResponse findByCode(String code);

    List<CategoryResponse> search(String q);

    CategoryResponse create(CategoryCreateRequest request);

    CategoryResponse update(Long id, CategoryUpdateRequest request);

    void delete(Long id);
}
