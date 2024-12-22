package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.shared.dto.request.CreateUserRequest;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("${api.prefix}/employees")
class EmployeeController {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("")
    ResponseEntity<ApiResponse<EmployeeResponse>> createEmployee(@RequestBody @Valid CreateUserRequest request) {
        EmployeeResponse employee = employeeService.createEmployee(request);

//        Builder Design pattern have been used
        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status(SUCCESS_MESSAGE)
                .results(employee)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
