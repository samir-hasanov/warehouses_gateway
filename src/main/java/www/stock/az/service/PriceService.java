package www.stock.az.service;

import www.stock.az.dto.request.warehousesorder.PriceCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceUpdateRequest;
import www.stock.az.dto.response.warehousesorder.PriceResponse;

import java.util.List;

public interface PriceService {
    
    PriceResponse create(PriceCreateRequest request);
    
    PriceResponse findById(Long id);
    
    List<PriceResponse> findByProductId(Long productId);
    
    List<PriceResponse> findByWarehouseId(Long warehouseId);
    
    PriceResponse getCurrentPrice(Long productId, Long warehouseId, String priceType);
    
    List<PriceResponse> getPriceHistory(Long productId, Long warehouseId);
    
    PriceResponse update(Long id, PriceUpdateRequest request);
    
    void delete(Long id);
}
