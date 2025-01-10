package com.huseynov.restaurant.customer;

import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.request.RegisterRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import org.springframework.security.core.Authentication;

public interface CustomerService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse authenticateCustomer(LoginRequest request);
    LoginResponse authenticationProcess(Authentication authentication);

}
