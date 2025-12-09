package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesOrder;
import www.stock.az.dto.request.warehousesorder.DiscountCreateRequest;
import www.stock.az.dto.request.warehousesorder.DiscountUpdateRequest;
import www.stock.az.dto.response.warehousesorder.DiscountResponse;
import www.stock.az.service.DiscountService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final WebClientWarehousesOrder webClientWarehousesOrder;

    @Override
    public DiscountResponse create(DiscountCreateRequest request) {
        return webClientWarehousesOrder.createDiscount(request);
    }

    @Override
    public DiscountResponse findById(Long id) {
        return webClientWarehousesOrder.findDiscountById(id);
    }

    @Override
    public DiscountResponse findByCode(String code) {
        return webClientWarehousesOrder.findDiscountByCode(code);
    }

    @Override
    public List<DiscountResponse> findAll() {
        return webClientWarehousesOrder.findAllDiscounts();
    }

    @Override
    public List<DiscountResponse> findActive() {
        return webClientWarehousesOrder.findActiveDiscounts();
    }

    @Override
    public List<DiscountResponse> search(String query) {
        return webClientWarehousesOrder.searchDiscounts(query);
    }

    @Override
    public DiscountResponse update(Long id, DiscountUpdateRequest request) {
        return webClientWarehousesOrder.updateDiscount(id, request);
    }

    @Override
    public void delete(Long id) {
        webClientWarehousesOrder.deleteDiscount(id);
    }

    @Override
    public DiscountResponse validate(String code, BigDecimal orderAmount) {
        return webClientWarehousesOrder.validateDiscount(code, orderAmount);
    }
}
