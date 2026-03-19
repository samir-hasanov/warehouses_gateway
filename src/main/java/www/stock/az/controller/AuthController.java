package www.stock.az.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequestMapping("/api/1.1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserInfoResponse>> listAllUsers(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(authService.listAllUsers(authorization));
    }

    @PostMapping("/user")
    public ResponseEntity<UserInfoResponse> createUserByAdmin(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody CreateUserByAdminRequest request) {
        return ResponseEntity.ok(authService.createUserByAdmin(authorization, request));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserInfoResponse> updateUser(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(authService.updateUser(authorization, id, request));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        authService.deleteUser(authorization, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/{id}/active")
    public ResponseEntity<Void> setUserActive(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @RequestParam boolean active) {
        authService.setUserActive(authorization, id, active);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{id}/roles")
    public ResponseEntity<Void> setUserRoles(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody SetUserRolesRequest request) {
        authService.setUserRoles(authorization, id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleResponse>> listRoles(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return ResponseEntity.ok(authService.listRoles(authorization));
    }
}

