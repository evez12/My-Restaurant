package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.request.CreateUserRequest;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(CreateUserRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);
}
