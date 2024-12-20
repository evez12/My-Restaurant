package com.huseynov.restaurant.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController()
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees-with-detail")
    public List<EmployeeWithDetailDTO> getAllEmployeesWithDetail() {
        return employeeService.getAllEmployeesWithDetail();
    }


}
