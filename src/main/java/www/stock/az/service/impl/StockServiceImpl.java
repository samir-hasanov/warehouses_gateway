package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.response.warehousesmanagement.StockResponse;
import www.stock.az.service.StockService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<StockResponse> findAll() {
        return webClientWarehousesManagement.findAllStocks();
    }

    @Override
    public StockResponse findById(Long id) {
        return webClientWarehousesManagement.findStockById(id);
    }

    @Override
    public List<StockResponse> findByWarehouseId(Long warehouseId) {
        return webClientWarehousesManagement.findStocksByWarehouse(warehouseId);
    }

    @Override
    public List<StockResponse> findByProductId(Long productId) {
        return webClientWarehousesManagement.findStocksByProduct(productId);
    }

    @Override
    public List<StockResponse> findLowStockItems(Long warehouseId) {
        return webClientWarehousesManagement.findLowStockItems(warehouseId);
    }
}

