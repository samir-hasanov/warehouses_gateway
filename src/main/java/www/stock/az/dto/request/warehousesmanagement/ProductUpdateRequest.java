package www.stock.az.dto.request.warehousesmanagement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    @NotBlank(message = "Məhsul adı mütləqdir")
    private String name;

    private String description;

    @NotNull(message = "Kateqoriya mütləqdir")
    private Long categoryId;

    @NotNull(message = "Brend mütləqdir")
    private Long brandId;

    @NotBlank(message = "Vahid mütləqdir")
    private String unit;

    private BigDecimal weight;

    private String dimensions;

    private Boolean isActive;

    @NotBlank(message = "Əsas barcode mütləqdir")
    private String mainBarcode;

    private BigDecimal defaultMinStockLevel;

    private BigDecimal taxRate;
}

