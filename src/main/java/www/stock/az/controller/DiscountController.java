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
import www.stock.az.dto.request.warehousesorder.DiscountCreateRequest;
import www.stock.az.dto.request.warehousesorder.DiscountUpdateRequest;
import www.stock.az.dto.response.warehousesorder.DiscountResponse;
import www.stock.az.service.DiscountService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/1.1/discounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Discounts", description = "Discount management API endpoints")
public class DiscountController {
    
    private final DiscountService discountService;
    
    @PostMapping
    @Operation(summary = "Create a new discount", description = "Creates a new discount rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Discount created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<DiscountResponse> createDiscount(@Valid @RequestBody DiscountCreateRequest request) {
        try {
            DiscountResponse response = discountService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get discount by ID", description = "Returns a discount by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount found"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<DiscountResponse> getDiscountById(
            @Parameter(description = "Discount ID", required = true) @PathVariable Long id) {
        try {
            DiscountResponse discount = discountService.findById(id);
            return ResponseEntity.ok(discount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get discount by code", description = "Returns a discount by its code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount found"),
            @ApiResponse(responseCode = "404", description = "Discount not found")
    })
    public ResponseEntity<DiscountResponse> getDiscountByCode(
            @Parameter(description = "Discount code", required = true) @PathVariable String code) {
        try {
            DiscountResponse discount = discountService.findByCode(code);
            return ResponseEntity.ok(discount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Get all discounts", description = "Returns a list of all discounts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of discounts")
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        try {
            List<DiscountResponse> discounts = discountService.findAll();
            return ResponseEntity.ok(discounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get active discounts", description = "Returns a list of currently active discounts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved active discounts")
    public ResponseEntity<List<DiscountResponse>> getActiveDiscounts() {
        try {
            List<DiscountResponse> discounts = discountService.findActive();
            return ResponseEntity.ok(discounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/validate")
    @Operation(summary = "Validate discount code", description = "Validates a discount code for a given order amount")
    @ApiResponse(responseCode = "200", description = "Discount validation result")
    public ResponseEntity<DiscountResponse> validateDiscount(
            @Parameter(description = "Discount code", required = true) @RequestParam String code,
            @Parameter(description = "Order amount", required = true) @RequestParam BigDecimal orderAmount) {
        DiscountResponse discount = discountService.validate(code, orderAmount);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
