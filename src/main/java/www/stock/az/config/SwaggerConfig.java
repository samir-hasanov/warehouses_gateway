package www.stock.az.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI warehousesGatewayOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8085");
        devServer.setDescription("Development server");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.stock.az");
        prodServer.setDescription("Production server");

        Contact contact = new Contact();
        contact.setEmail("info@stock.az");
        contact.setName("Stock.az Team");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Warehouses Gateway API")
                .version("1.0.0")
                .contact(contact)
                .description("API Gateway for Warehouses Management System. " +
                        "This service acts as a gateway between frontend and microservices.")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
