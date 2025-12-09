package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import www.stock.az.dto.response.warehousesmanagement.BrandResponse;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses-management.client")
public class WarehousesManagementClient {
    private String baseUrl;


}
