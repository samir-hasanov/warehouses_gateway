package www.stock.az.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.stock.az.dto.response.warehousesmanagement.StockResponse;
import www.stock.az.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/1.1/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Stocks", description = "Stock management API endpoints")
public class StockController {

    private final StockService stockService;

    @GetMapping
    @Operation(summary = "Get all stocks", description = "Returns a list of all stocks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of stocks")
    public ResponseEntity<List<StockResponse>> getAllStocks() {
        try {
            List<StockResponse> stocks = stockService.findAll();
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get stock by ID", description = "Returns a stock by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock found"),
            @ApiResponse(responseCode = "404", description = "Stock not found")
    })
    public ResponseEntity<StockResponse> getStockById(
            @Parameter(description = "Stock ID", required = true) @PathVariable Long id) {
        try {
            StockResponse stock = stockService.findById(id);
            return ResponseEntity.ok(stock);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/warehouse/{warehouseId}")
    @Operation(summary = "Get stocks by warehouse", description = "Returns all stocks for a specific warehouse")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved stocks")
    public ResponseEntity<List<StockResponse>> getStocksByWarehouse(
            @Parameter(description = "Warehouse ID", required = true) @PathVariable Long warehouseId) {
        try {
            List<StockResponse> stocks = stockService.findByWarehouseId(warehouseId);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get stocks by product", description = "Returns all stocks for a specific product")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved stocks")
    public ResponseEntity<List<StockResponse>> getStocksByProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long productId) {
        try {
            List<StockResponse> stocks = stockService.findByProductId(productId);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get low stock items", description = "Returns stocks that are below minimum level")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved low stock items")
    public ResponseEntity<List<StockResponse>> getLowStockItems(
            @Parameter(description = "Warehouse ID (optional)") @RequestParam(required = false) Long warehouseId) {
        try {
            List<StockResponse> stocks = stockService.findLowStockItems(warehouseId);
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

