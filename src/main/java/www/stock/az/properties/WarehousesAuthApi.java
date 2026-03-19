package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses-auth.api")
public class WarehousesAuthApi {
    private String AuthController_register;
    private String AuthController_login;
    private String AuthController_refresh;
    private String AuthController_logout;
    private String AuthController_listUsers;
    private String AuthController_createUser;
    private String AuthController_updateUser;
    private String AuthController_deleteUser;
    private String AuthController_setUserActive;
    private String AuthController_setUserRoles;
    private String AuthController_listRoles;
}
