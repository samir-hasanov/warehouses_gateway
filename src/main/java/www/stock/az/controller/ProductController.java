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
import www.stock.az.dto.request.warehousesmanagement.ProductCreateRequest;
import www.stock.az.dto.request.warehousesmanagement.ProductUpdateRequest;
import www.stock.az.dto.response.warehousesmanagement.ProductResponse;
import www.stock.az.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1.1/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "Product management API endpoints")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all active products", description = "Returns a list of all active products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products")
    public ResponseEntity<List<ProductResponse>> getAllActiveProducts() {
        try {
            List<ProductResponse> products = productService.findAllActive();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        try {
            ProductResponse product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get product by code", description = "Returns a product by its unique code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductByCode(
            @Parameter(description = "Product code", required = true) @PathVariable String code) {
        try {
            ProductResponse product = productService.findByCode(code);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/barcode/{barcode}")
    @Operation(summary = "Get product by barcode", description = "Returns a product by its barcode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductByBarcode(
            @Parameter(description = "Product barcode", required = true) @PathVariable String barcode) {
        try {
            // Barcode-u təmizlə: boşluqları sil və trim et
            String cleanedBarcode = barcode != null ? barcode.replaceAll("\\s+", "").trim() : "";
            if (cleanedBarcode.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            ProductResponse product = productService.findByBarcode(cleanedBarcode);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by query string")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @Parameter(description = "Search query", required = true) @RequestParam String q) {
        try {
            List<ProductResponse> products = productService.search(q);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        try {
            ProductResponse response = productService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Məhsul yaradılarkən xəta baş verdi");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {
        try {
            ProductResponse response = productService.update(id, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Soft deletes a product by ID (sets isActive to false)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
