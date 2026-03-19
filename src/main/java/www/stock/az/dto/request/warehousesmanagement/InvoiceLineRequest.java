package www.stock.az.dto.request.warehousesmanagement;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceLineRequest {

    private Long productId;

    // Frontend-dən gələn barkod/kod və məhsul adı
    private String productCode;
    private String productName;

    // 13 rəqəmli barcode (Excel-dəki "Barcode" sütunu) – management servisinə ötürülür
    private String stockNumber;

    @NotNull
    @DecimalMin("0.0001")
    private BigDecimal quantity;

    @NotNull
    @DecimalMin("0.0000")
    private BigDecimal unitPrice;

    private BigDecimal vatAmount;
}
