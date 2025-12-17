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
import www.stock.az.dto.request.warehousesmanagement.StockMovementCreateRequest;
import www.stock.az.dto.response.warehousesmanagement.StockMovementResponse;
import www.stock.az.service.StockMovementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.1/stock-movements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Stock Movements", description = "Stock movement management API endpoints")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    @Operation(summary = "Get all stock movements", description = "Retrieves all stock movements with optional type filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock movements retrieved successfully")
    })
    public ResponseEntity<?> getAllStockMovements(@RequestParam(required = false) String type) {
        try {
            List<StockMovementResponse> movements = stockMovementService.getAllStockMovements(type);
            return ResponseEntity.ok(movements);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Stok hərəkətləri yüklənərkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping
    @Operation(summary = "Create stock movement", description = "Creates a new stock movement (STOCK_IN, STOCK_OUT, TRANSFER)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock movement created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createStockMovement(@Valid @RequestBody StockMovementCreateRequest request) {
        try {
            StockMovementResponse response = stockMovementService.createStockMovement(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Stok hərəkəti yaradılarkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

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

    @PostMapping("/{id}/approve")
    @Operation(summary = "Approve stock movement", description = "Approves a pending stock movement and executes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock movement approved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or movement cannot be approved")
    })
    public ResponseEntity<?> approveStockMovement(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> request) {
        try {
            String approvedBy = request != null && request.containsKey("approvedBy") 
                    ? request.get("approvedBy") 
                    : "SYSTEM";
            StockMovementResponse response = stockMovementService.approveStockMovement(id, approvedBy);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Hərəkət təsdiqlənərkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel stock movement", description = "Cancels a pending stock movement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock movement cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or movement cannot be cancelled")
    })
    public ResponseEntity<?> cancelStockMovement(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> request) {
        try {
            String reason = request != null && request.containsKey("reason") 
                    ? request.get("reason") 
                    : "İstifadəçi tərəfindən ləğv edildi";
            StockMovementResponse response = stockMovementService.cancelStockMovement(id, reason);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Hərəkət ləğv edilərkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
