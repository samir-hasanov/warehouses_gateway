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
import www.stock.az.dto.request.warehousesmanagement.WarehouseCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.WarehouseUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.WarehouseResponse;
import www.stock.az.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/1.1/warehouses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Warehouses", description = "Warehouse management API endpoints")
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    @Operation(summary = "Get all active warehouses", description = "Returns a list of all active warehouses")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of warehouses")
    public ResponseEntity<List<WarehouseResponse>> getAllActiveWarehouses() {
        try {
            List<WarehouseResponse> warehouses = warehouseService.findAllActive();
            return ResponseEntity.ok(warehouses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get warehouse by ID", description = "Returns a warehouse by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse found"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<WarehouseResponse> getWarehouseById(
            @Parameter(description = "Warehouse ID", required = true) @PathVariable Long id) {
        try {
            WarehouseResponse warehouse = warehouseService.findById(id);
            return ResponseEntity.ok(warehouse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get warehouse by code", description = "Returns a warehouse by its unique code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse found"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<WarehouseResponse> getWarehouseByCode(
            @Parameter(description = "Warehouse code", required = true) @PathVariable String code) {
        try {
            WarehouseResponse warehouse = warehouseService.findByCode(code);
            return ResponseEntity.ok(warehouse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new warehouse", description = "Creates a new warehouse with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Warehouse created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createWarehouse(@Valid @RequestBody WarehouseCreateRequest request) {
        try {
            WarehouseResponse response = warehouseService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update warehouse", description = "Updates an existing warehouse by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> updateWarehouse(
            @Parameter(description = "Warehouse ID", required = true) @PathVariable Long id,
            @Valid @RequestBody WarehouseUpdateRequest request) {
        try {
            WarehouseResponse response = warehouseService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete warehouse", description = "Soft deletes a warehouse by ID (sets isActive to false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Warehouse deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    public ResponseEntity<?> deleteWarehouse(
            @Parameter(description = "Warehouse ID", required = true) @PathVariable Long id) {
        try {
            warehouseService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Exception will be handled by GlobalExceptionHandler
            throw e;
        }
    }
}
