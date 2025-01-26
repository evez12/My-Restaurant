package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.data.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee getEmployee();
}



























