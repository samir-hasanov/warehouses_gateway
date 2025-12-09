package www.stock.az.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.stock.az.dto.request.warehousesorder.PriceCreateRequest;
import www.stock.az.dto.request.warehousesorder.PriceUpdateRequest;
import www.stock.az.dto.response.warehousesorder.PriceResponse;
import www.stock.az.service.PriceService;

import java.util.List;

@RestController
@RequestMapping("/api/1.1/prices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Prices", description = "Price management API endpoints")
public class PriceController {
    
    private final PriceService priceService;
    
    @PostMapping
    @Operation(summary = "Create a new price", description = "Creates a new price for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Price created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PriceResponse> createPrice(@Valid @RequestBody PriceCreateRequest request) {
        try {
            PriceResponse response = priceService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get prices by product ID", description = "Returns all prices for a specific product")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved prices")
    public ResponseEntity<List<PriceResponse>> getPricesByProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long productId) {
        try {
            List<PriceResponse> prices = priceService.findByProductId(productId);
            return ResponseEntity.ok(prices);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/product/{productId}/current")
    @Operation(summary = "Get current price", description = "Returns the current active price for a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current price found"),
            @ApiResponse(responseCode = "404", description = "Current price not found")
    })
    public ResponseEntity<PriceResponse> getCurrentPrice(
            @Parameter(description = "Product ID", required = true) @PathVariable Long productId,
            @Parameter(description = "Warehouse ID") @RequestParam(required = false) Long warehouseId,
            @Parameter(description = "Price type", required = true) @RequestParam(defaultValue = "SELLING") String priceType) {
        try {
            PriceResponse price = priceService.getCurrentPrice(productId, warehouseId, priceType);
            return ResponseEntity.ok(price);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
