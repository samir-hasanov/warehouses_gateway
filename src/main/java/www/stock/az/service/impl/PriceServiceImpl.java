package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesOrder;
import www.stock.az.dto.request.warehousesorder.PriceCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceUpdateRequest;
import www.stock.az.dto.response.warehousesorder.PriceResponse;
import www.stock.az.service.PriceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final WebClientWarehousesOrder webClientWarehousesOrder;

    @Override
    public PriceResponse create(PriceCreateRequest request) {
        return webClientWarehousesOrder.createPrice(request);
    }

    @Override
    public PriceResponse findById(Long id) {
        return webClientWarehousesOrder.findPriceById(id);
    }

    @Override
    public List<PriceResponse> findByProductId(Long productId) {
        return webClientWarehousesOrder.getPricesByProduct(productId);
    }

    @Override
    public List<PriceResponse> findByWarehouseId(Long warehouseId) {
        return webClientWarehousesOrder.findByWarehouseId(warehouseId);
    }

    @Override
    public PriceResponse getCurrentPrice(Long productId, Long warehouseId, String priceType) {
        return webClientWarehousesOrder.getCurrentPrice(productId, warehouseId, priceType);
    }

    @Override
    public List<PriceResponse> getPriceHistory(Long productId, Long warehouseId) {
        return webClientWarehousesOrder.getPriceHistory(productId, warehouseId);
    }

    @Override
    public PriceResponse update(Long id, PriceUpdateRequest request) {
        return webClientWarehousesOrder.updatePrice(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesOrder.deletePrice(id);
    }
}
