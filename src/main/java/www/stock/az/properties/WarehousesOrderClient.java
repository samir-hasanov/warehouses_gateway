package www.stock.az.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app.warehouses_order_service.client")
public class WarehousesOrderClient {

    private String baseUrl;
}
