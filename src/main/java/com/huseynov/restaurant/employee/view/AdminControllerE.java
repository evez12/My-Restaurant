package com.huseynov.restaurant.employee.view;

import com.huseynov.restaurant.employee.AuthEmployeeService;
import com.huseynov.restaurant.employee.CreateEmployeeRequest;
import com.huseynov.restaurant.employee.EmployeeService;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}/admin")
@CrossOrigin()
@RequiredArgsConstructor
@Slf4j()
public class AdminControllerE {
    private final EmployeeService employeeService;
    private final AuthEmployeeService authEmployeeService;


    @PostMapping("/employees")
    ResponseEntity<ApiResponse<RegisterResponse>> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        log.info("EmployeeController::createEmployee request body {}", request);

        RegisterResponse employee = authEmployeeService.createEmployee(request);

        //        Builder Design pattern have been used
        ApiResponse<RegisterResponse> response = ApiResponse.<RegisterResponse>builder()
                .status("Employee created successfully")
                .results(employee)
                .build();

        log.info("EmployeeController::createEmployee response: {}", employee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/employees/{id}")
    ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        log.info("AdminControllerE::getEmployeeById, id: {}", id);

        EmployeeDTO employee = employeeService.getEmployeeById(id);

        ApiResponse<EmployeeDTO> response = ApiResponse.<EmployeeDTO>builder()
                .status("Employee found successfully")
                .results(employee)
                .build();

        log.info("AdminControllerE:getEmployeeById response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployees() {
        log.info("AdminControllerE::getAllEmployees execution started");
        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .status("Get all Employees successfully")
                .results(employees)
                .build();

        log.info("AdminControllerE:getEmployees response: {}", response);
        return ResponseEntity.ok(response);
    }


}
