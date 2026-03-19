package www.stock.az.dto.response.warehousesauth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    String accessToken;
    String refreshToken;
    UserInfoResponse user;
}
