package com.huseynov.restaurant.employee.view;

import com.huseynov.restaurant.employee.AuthEmployeeService;
import com.huseynov.restaurant.employee.CreateEmployeeRequest;
import com.huseynov.restaurant.employee.EmployeeMapper;
import com.huseynov.restaurant.employee.EmployeeService;
import com.huseynov.restaurant.employee.data.Employee;
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
    private final EmployeeMapper employeeMapper;
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
        log.info("EmployeeController::createEmployee response employee, {}", employee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/employees/{id}")
    ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        log.info("AdminControllerE::getEmployeeById, id: {}", id);

        Employee employeeDB = employeeService.getEmployeeById(id);
        log.info("EmployeeDB: {}", employeeDB);

        EmployeeDTO employee = employeeMapper.entityToDTO(employeeDB);

        ApiResponse<EmployeeDTO> response = ApiResponse.<EmployeeDTO>builder()
                .status("Employee found successfully")
                .results(employee)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employees")
    ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        log.info("AdminControllerE::getAllEmployees");
        List<Employee> employeesDB = employeeService.getAllEmployees();

        List<EmployeeResponse> employees = employeesDB.stream()
                .map(employeeMapper::entityToResponse)
                .toList();

        ApiResponse<List<EmployeeResponse>> response = ApiResponse.<List<EmployeeResponse>>builder()
                .status("All Employees found successfully")
                .results(employees)
                .build();
        return ResponseEntity.ok(response);
    }


}
