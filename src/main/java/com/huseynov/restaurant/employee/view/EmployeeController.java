package com.huseynov.restaurant.employee.view;

import com.huseynov.restaurant.employee.EmployeeService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${api.prefix}/employee")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j()
class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("")
    ResponseEntity<ApiResponse<EmployeeDTO>> getEmployee() {
        log.info("EmployeeController:getEmployee() execution started");
        EmployeeDTO employee = employeeService.getEmployee();

        ApiResponse<EmployeeDTO> response = ApiResponse.<EmployeeDTO>builder()
                .status("Employee found successfully")
                .results(employee)
                .build();

        log.info("EmployeeController:getEmployee response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeProfile() {
        log.info("EmployeeController:getEmployeeProfile execution started");
        EmployeeDTO employee = employeeService.getEmployee();

        ApiResponse<EmployeeDTO> response = ApiResponse.<EmployeeDTO>builder()
                .status("Employee found successfully")
                .results(employee)
                .build();

        log.info("EmployeeController:getEmployeeProfile response: {}", response);
        return ResponseEntity.ok(response);
    }


}
