package www.stock.az.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import www.stock.az.dto.request.warehousesauth.CreateUserByAdminRequest;
import www.stock.az.dto.request.warehousesauth.LoginRequest;
import www.stock.az.dto.request.warehousesauth.RefreshTokenRequest;
import www.stock.az.dto.request.warehousesauth.RegisterRequest;
import www.stock.az.dto.request.warehousesauth.SetUserRolesRequest;
import www.stock.az.dto.request.warehousesauth.UpdateUserRequest;
import www.stock.az.dto.response.warehousesauth.AuthResponse;
import www.stock.az.dto.response.warehousesauth.RoleResponse;
import www.stock.az.dto.response.warehousesauth.UserInfoResponse;
import www.stock.az.properties.WarehousesAuthApi;
import www.stock.az.properties.WarehousesAuthClient;

import java.util.List;

@Component
@Slf4j
public class WebClientWarehousesAuth {

    private final WebClient webClient;
    private final WarehousesAuthApi warehousesAuthApi;

    public WebClientWarehousesAuth(WebClient.Builder webClientBuilder,
                                   WarehousesAuthClient warehousesAuthClient,
                                   WarehousesAuthApi warehousesAuthApi) {
        this.webClient = webClientBuilder.baseUrl(warehousesAuthClient.getBaseUrl()).build();
        this.warehousesAuthApi = warehousesAuthApi;
    }

    public AuthResponse register(RegisterRequest request) {
        return webClient.post()
                .uri(warehousesAuthApi.getAuthController_register())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    public AuthResponse login(LoginRequest request) {
        return webClient.post()
                .uri(warehousesAuthApi.getAuthController_login())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    public AuthResponse refresh(RefreshTokenRequest request) {
        return webClient.post()
                .uri(warehousesAuthApi.getAuthController_refresh())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();
    }

    public void logout(RefreshTokenRequest request) {
        webClient.post()
                .uri(warehousesAuthApi.getAuthController_logout())
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private WebClient.RequestHeadersSpec<?> withAuth(WebClient.RequestHeadersSpec<?> spec, String authorization) {
        if (authorization != null && !authorization.isBlank()) {
            return spec.header("Authorization", authorization);
        }
        return spec;
    }

    public List<UserInfoResponse> listUsers(String authorization) {
        return withAuth(webClient.get().uri(warehousesAuthApi.getAuthController_listUsers()), authorization)
                .retrieve()
                .bodyToFlux(UserInfoResponse.class)
                .collectList()
                .block();
    }

    public UserInfoResponse createUser(String authorization, CreateUserByAdminRequest request) {
        return withAuth(webClient.post()
                .uri(warehousesAuthApi.getAuthController_createUser())
                .bodyValue(request), authorization)
                .retrieve()
                .bodyToMono(UserInfoResponse.class)
                .block();
    }

    public UserInfoResponse updateUser(String authorization, Long id, UpdateUserRequest request) {
        String uri = warehousesAuthApi.getAuthController_updateUser().replace("{id}", String.valueOf(id));
        return withAuth(webClient.put()
                .uri(uri)
                .bodyValue(request), authorization)
                .retrieve()
                .bodyToMono(UserInfoResponse.class)
                .block();
    }

    public void deleteUser(String authorization, Long id) {
        String uri = warehousesAuthApi.getAuthController_deleteUser().replace("{id}", String.valueOf(id));
        withAuth(webClient.delete().uri(uri), authorization)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void setUserActive(String authorization, Long id, boolean active) {
        String uri = warehousesAuthApi.getAuthController_setUserActive().replace("{id}", String.valueOf(id)) + "?active=" + active;
        withAuth(webClient.patch().uri(uri), authorization)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void setUserRoles(String authorization, Long id, SetUserRolesRequest request) {
        String uri = warehousesAuthApi.getAuthController_setUserRoles().replace("{id}", String.valueOf(id));
        withAuth(webClient.put()
                .uri(uri)
                .bodyValue(request), authorization)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<RoleResponse> listRoles(String authorization) {
        return withAuth(webClient.get().uri(warehousesAuthApi.getAuthController_listRoles()), authorization)
                .retrieve()
                .bodyToFlux(RoleResponse.class)
                .collectList()
                .block();
    }
}

