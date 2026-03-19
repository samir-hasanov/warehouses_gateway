package www.stock.az.dto.request.warehousesauth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotBlank
    @Size(max = 100)
    private String username;
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
    @Size(max = 20)
    private String phoneNumber;
    private Boolean active;
}

