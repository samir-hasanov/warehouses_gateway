package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;
import www.stock.az.service.StockMovementService;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final WebClientWarehousesManagement webClientWarehousesManagement;

    @Override
    public StockMovementResponse addStockByBarcode(StockInRequest request) {
        return webClientWarehousesManagement.addStockByBarcode(request);
    }
}

