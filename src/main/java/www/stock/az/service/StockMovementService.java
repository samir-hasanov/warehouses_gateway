package www.stock.az.service;

import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.request.warehousesmanagement.StockMovementCreateRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;

import java.util.List;

public interface StockMovementService {
    List<StockMovementResponse> getAllStockMovements(String type);
    
    StockMovementResponse createStockMovement(StockMovementCreateRequest request);
    
    StockMovementResponse addStockByBarcode(StockInRequest request);
    
    StockMovementResponse approveStockMovement(Long id, String approvedBy);
    
    StockMovementResponse cancelStockMovement(Long id, String reason);
}

