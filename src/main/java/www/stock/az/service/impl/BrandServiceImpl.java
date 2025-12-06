package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.BrandCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.BrandUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.BrandResponse;
import www.stock.az.service.BrandService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<BrandResponse> findAllActive() {
        return webClientWarehousesManagement.findAllActiveBrands();
    }

    @Override
    public BrandResponse findById(Long id) {
        return webClientWarehousesManagement.findBrandById(id);
    }

    @Override
    public BrandResponse findByCode(String code) {
        return webClientWarehousesManagement.findBrandByCode(code);
    }

    @Override
    public List<BrandResponse> search(String q) {
        return webClientWarehousesManagement.searchBrands(q);
    }

    @Override
    public BrandResponse create(BrandCreateRequest request) {
        return webClientWarehousesManagement.createBrand(request);
    }

    @Override
    public BrandResponse update(Long id, BrandUpdateRequest request) {
        return webClientWarehousesManagement.updateBrand(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesManagement.deleteBrand(id);
    }
}
