package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import com.huseynov.restaurant.shared.dto.response.RegisterResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.prefix}/employees")
@CrossOrigin
@Slf4j(topic = "EMPLOYEE_CONTROLLER")
class EmployeeController {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/")
//    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<ApiResponse<RegisterResponse>> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        log.info("EmployeeController::createEmployee request body {}", request);

        RegisterResponse employee = employeeService.createEmployee(request);
//        Builder Design pattern have been used
        ApiResponse<RegisterResponse> response = ApiResponse.<RegisterResponse>builder()
                .status(SUCCESS_MESSAGE)
                .results(employee)
                .build();
        log.info("EmployeeController::createEmployee response employee, {}", employee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String helloEmployee() {
        return "Hello Employee";
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);

        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status(SUCCESS_MESSAGE)
                .results(employee)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .status(SUCCESS_MESSAGE)
                .results(employees)
                .build();
        return ResponseEntity.ok(response);
    }

}
