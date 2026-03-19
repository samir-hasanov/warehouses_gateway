package www.stock.az.dto.request.warehousesauth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserRolesRequest {
    @NotNull
    private List<String> roleNames;
}
