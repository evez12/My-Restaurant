package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.shared.dto.request.LoginRequest;
import com.huseynov.restaurant.shared.dto.response.LoginResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import org.springframework.security.core.Authentication;

public interface AuthEmployeeService {

    RegisterResponse createEmployee(CreateEmployeeRequest request);

    LoginResponse authenticateEmployee(LoginRequest request);

    LoginResponse authenticationProcess(Authentication authentication);

    RegisterResponse generateRegisterResponse(Employee employee, String email, String password);
}
