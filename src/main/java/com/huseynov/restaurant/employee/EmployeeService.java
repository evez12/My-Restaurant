package com.huseynov.restaurant.employee;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeWithDetailDTO> getAllEmployeesWithDetail();

    Employee getEmployeeById(Long id);
}
