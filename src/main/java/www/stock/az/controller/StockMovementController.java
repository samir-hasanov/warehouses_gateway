package www.stock.az.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.stock.az.dto.request.warehousesmanagement.StockInRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;
import www.stock.az.service.StockMovementService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/1.1/stock-movements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Stock Movements", description = "Stock movement management API endpoints")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping("/stock-in-by-barcode")
    @Operation(summary = "Add stock by barcode", description = "Adds stock to a warehouse using product barcode scanner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or product not found")
    })
    public ResponseEntity<?> addStockByBarcode(@Valid @RequestBody StockInRequest request) {
        try {
            // Barcode-u təmizlə: boşluqları sil və trim et
            if (request.getProductBarcode() != null) {
                String cleanedBarcode = request.getProductBarcode().replaceAll("\\s+", "").trim();
                request.setProductBarcode(cleanedBarcode);
            }
            
            StockMovementResponse response = stockMovementService.addStockByBarcode(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Stok əlavə edilərkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
