package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.WarehouseCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.WarehouseUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.WarehouseResponse;
import www.stock.az.service.WarehouseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<WarehouseResponse> findAllActive() {
        return webClientWarehousesManagement.findAllActiveWarehouses();
    }

    @Override
    public WarehouseResponse findById(Long id) {
        return webClientWarehousesManagement.findWarehouseById(id);
    }

    @Override
    public WarehouseResponse findByCode(String code) {
        return webClientWarehousesManagement.findWarehouseByCode(code);
    }

    @Override
    public WarehouseResponse create(WarehouseCreateRequest request) {
        return webClientWarehousesManagement.createWarehouse(request);
    }

    @Override
    public WarehouseResponse update(Long id, WarehouseUpdateRequest request) {
        return webClientWarehousesManagement.updateWarehouse(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesManagement.deleteWarehouse(id);
    }
}
