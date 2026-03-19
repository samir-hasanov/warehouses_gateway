package www.stock.az.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import www.stock.az.config.WebClientWarehousesManagement;
import www.stock.az.dto.request.warehousesmanagement.InvoiceCreateRequest;
import www.stock.az.dto.response.warehousesmanagement.InvoiceResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/1.1/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Invoices (Qaimələr)", description = "Invoice management API - proxy to warehouses_management")
public class InvoiceController {

    private final WebClientWarehousesManagement webClientManagement;

    @PostMapping
    @Operation(summary = "Create invoice", description = "Yeni qaimə yaradır")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Qaimə yaradıldı"),
            @ApiResponse(responseCode = "400", description = "Yanlış məlumat")
    })
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceCreateRequest request) {
        try {
            InvoiceResponse response = webClientManagement.createInvoice(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    @ApiResponse(responseCode = "200", description = "Qaimə tapıldı")
    public ResponseEntity<InvoiceResponse> getById(
            @Parameter(description = "Invoice ID", required = true) @PathVariable Long id) {
        try {
            InvoiceResponse response = webClientManagement.findInvoiceById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/number/{invoiceNumber}")
    @Operation(summary = "Get invoice by number")
    public ResponseEntity<InvoiceResponse> getByNumber(
            @Parameter(description = "Qaimə nömrəsi", required = true) @PathVariable String invoiceNumber) {
        try {
            InvoiceResponse response = webClientManagement.findInvoiceByNumber(invoiceNumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    @Operation(summary = "Search invoices", description = "direction: IN/OUT, fromDate/toDate: ISO date-time")
    @ApiResponse(responseCode = "200", description = "Siyahı")
    public ResponseEntity<List<InvoiceResponse>> search(
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
        try {
            List<InvoiceResponse> list = webClientManagement.searchInvoices(direction, fromDate, toDate);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
