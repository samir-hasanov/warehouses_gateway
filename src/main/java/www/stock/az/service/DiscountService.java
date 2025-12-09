package www.stock.az.service;

import www.stock.az.dto.request.warehousesorder.DiscountCreateRequest;
import www.stock.az.dto.request.warehousesorder.DiscountUpdateRequest;
import www.stock.az.dto.response.warehousesorder.DiscountResponse;

import java.math.BigDecimal;
import java.util.List;

public interface DiscountService {
    
    DiscountResponse create(DiscountCreateRequest request);
    
    DiscountResponse findById(Long id);
    
    DiscountResponse findByCode(String code);
    
    List<DiscountResponse> findAll();
    
    List<DiscountResponse> findActive();
    
    List<DiscountResponse> search(String query);
    
    DiscountResponse update(Long id, DiscountUpdateRequest request);
    
    void delete(Long id);
    
    DiscountResponse validate(String code, BigDecimal orderAmount);
}
