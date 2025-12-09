package www.stock.az.service;

import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;

public interface StockMovementService {
    StockMovementResponse addStockByBarcode(StockInRequest request);
}

