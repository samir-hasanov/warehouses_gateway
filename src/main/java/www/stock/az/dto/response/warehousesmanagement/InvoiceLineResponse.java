package www.stock.az.dto.response.warehousesmanagement;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceLineResponse {
    private Long id;
    private Long productId;
    private String productCode;
    private String productName;
    // Management servisindən gələn stok nömrəsi / barcode
    private String stockNumber;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal amountNet;
    private BigDecimal vatAmount;
    private BigDecimal amountGross;
}
