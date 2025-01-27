package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.view.EmployeeDTO;
import com.huseynov.restaurant.employee.view.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO getEmployee();
}



























