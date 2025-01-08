package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface EmployeeService {
    RegisterResponse createEmployee(CreateEmployeeRequest request);

    LoginResponse authenticateEmployee(LoginRequest request);

    LoginResponse generateLoginResponse(Authentication authentication);

    RegisterResponse generateRegisterResponse(Employee employee, String email, String password);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);
}



























