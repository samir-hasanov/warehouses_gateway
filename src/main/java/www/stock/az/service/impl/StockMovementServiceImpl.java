package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.request.warehousesmanagement.StockMovementCreateRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;
import www.stock.az.service.StockMovementService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public List<StockMovementResponse> getAllStockMovements(String type) {
        return webClientWarehousesManagement.getAllStockMovements(type);
    }

    @Override
    public StockMovementResponse createStockMovement(StockMovementCreateRequest request) {
        return webClientWarehousesManagement.createStockMovement(request);
    }

    @Override
    public StockMovementResponse addStockByBarcode(StockInRequest request) {
        return webClientWarehousesManagement.addStockByBarcode(request);
    }

    @Override
    public StockMovementResponse approveStockMovement(Long id, String approvedBy) {
        return webClientWarehousesManagement.approveStockMovement(id, approvedBy);
    }

    @Override
    public StockMovementResponse cancelStockMovement(Long id, String reason) {
        return webClientWarehousesManagement.cancelStockMovement(id, reason);
    }
}

