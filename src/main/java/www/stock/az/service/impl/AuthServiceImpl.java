package www.stock.az.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import www.stock.az.config.WebClientWarehousesAuth;
import www.stock.az.dto.request.warehousesauth.CreateUserByAdminRequest;
import www.stock.az.dto.request.warehousesauth.LoginRequest;
import www.stock.az.dto.request.warehousesauth.RefreshTokenRequest;
import www.stock.az.dto.request.warehousesauth.RegisterRequest;
import www.stock.az.dto.request.warehousesauth.SetUserRolesRequest;
import www.stock.az.dto.request.warehousesauth.UpdateUserRequest;
import www.stock.az.dto.response.warehousesauth.AuthResponse;
import www.stock.az.dto.response.warehousesauth.RoleResponse;
import www.stock.az.dto.response.warehousesauth.UserInfoResponse;
import www.stock.az.service.AuthService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WebClientWarehousesAuth webClientWarehousesAuth;

    @Override
    public AuthResponse register(RegisterRequest request) {
        return webClientWarehousesAuth.register(request);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return webClientWarehousesAuth.login(request);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        return webClientWarehousesAuth.refresh(request);
    }

    @Override
    public void logout(RefreshTokenRequest request) {
        webClientWarehousesAuth.logout(request);
    }

    @Override
    public List<UserInfoResponse> listAllUsers(String authorization) {
        return webClientWarehousesAuth.listUsers(authorization);
    }

    @Override
    public UserInfoResponse createUserByAdmin(String authorization, CreateUserByAdminRequest request) {
        return webClientWarehousesAuth.createUser(authorization, request);
    }

    @Override
    public UserInfoResponse updateUser(String authorization, Long id, UpdateUserRequest request) {
        return webClientWarehousesAuth.updateUser(authorization, id, request);
    }

    @Override
    public void deleteUser(String authorization, Long id) {
        webClientWarehousesAuth.deleteUser(authorization, id);
    }

    @Override
    public void setUserActive(String authorization, Long id, boolean active) {
        webClientWarehousesAuth.setUserActive(authorization, id, active);
    }

    @Override
    public void setUserRoles(String authorization, Long id, SetUserRolesRequest request) {
        webClientWarehousesAuth.setUserRoles(authorization, id, request);
    }

    @Override
    public List<RoleResponse> listRoles(String authorization) {
        return webClientWarehousesAuth.listRoles(authorization);
    }
}

