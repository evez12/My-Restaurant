package com.huseynov.restaurant.shared;

import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@CrossOrigin()
@RequiredArgsConstructor
@Slf4j()
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final RouterService routerService;

    @PostMapping("/sign-in")
    ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("AuthController::login request body {}", request);

        LoginResponse loginResponse = routerService.login(request);
        ApiResponse<LoginResponse> response = ApiResponse.<LoginResponse>builder()
                .status("Sign-in successfully")
                .results(loginResponse)
                .build();

        log.info("AuthController::login response {}", loginResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("AuthController::register request body {}", request);

        RegisterResponse registerResponse = routerService.register(request);
        ApiResponse<RegisterResponse> response = ApiResponse.<RegisterResponse>builder()
                .status("Customer registered successfully")
                .results(registerResponse)
                .build();

        log.info("AuthController::register response {}", registerResponse);
        return ResponseEntity.ok(response);
    }

}
