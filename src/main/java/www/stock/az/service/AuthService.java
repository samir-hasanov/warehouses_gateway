package www.stock.az.service;

import www.stock.az.dto.request.warehousesauth.CreateUserByAdminRequest;
import www.stock.az.dto.request.warehousesauth.LoginRequest;
import www.stock.az.dto.request.warehousesauth.RefreshTokenRequest;
import www.stock.az.dto.request.warehousesauth.RegisterRequest;
import www.stock.az.dto.request.warehousesauth.SetUserRolesRequest;
import www.stock.az.dto.request.warehousesauth.UpdateUserRequest;
import www.stock.az.dto.response.warehousesauth.AuthResponse;
import www.stock.az.dto.response.warehousesauth.RoleResponse;
import www.stock.az.dto.response.warehousesauth.UserInfoResponse;

import java.util.List;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refresh(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);

    List<UserInfoResponse> listAllUsers(String authorization);

    UserInfoResponse createUserByAdmin(String authorization, CreateUserByAdminRequest request);

    UserInfoResponse updateUser(String authorization, Long id, UpdateUserRequest request);

    void deleteUser(String authorization, Long id);

    void setUserActive(String authorization, Long id, boolean active);

    void setUserRoles(String authorization, Long id, SetUserRolesRequest request);

    List<RoleResponse> listRoles(String authorization);
}

