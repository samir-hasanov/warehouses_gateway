package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.CategoryCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.CategoryUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.CategoryResponse;
import www.stock.az.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<CategoryResponse> findAllActive() {
        return webClientWarehousesManagement.findAllActiveCategories();
    }

    @Override
    public CategoryResponse findById(Long id) {
        return webClientWarehousesManagement.findCategoryById(id);
    }

    @Override
    public CategoryResponse findByCode(String code) {
        return webClientWarehousesManagement.findCategoryByCode(code);
    }

    @Override
    public List<CategoryResponse> search(String q) {
        return webClientWarehousesManagement.searchCategories(q);
    }

    @Override
    public CategoryResponse create(CategoryCreateRequest request) {
        return webClientWarehousesManagement.createCategory(request);
    }

    @Override
    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        return webClientWarehousesManagement.updateCategory(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesManagement.deleteCategory(id);
    }
}
