package com.huseynov.restaurant.employee.view;

import com.huseynov.restaurant.employee.EmployeeService;
import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.shared.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("${api.prefix}/employee")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j()
class EmployeeController {

    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;


    @GetMapping("")
    ResponseEntity<ApiResponse<EmployeeResponse>> getEmployee() {
        Employee employeeDB = employeeService.getEmployee();
        EmployeeResponse employee = modelMapper.map(employeeDB, EmployeeResponse.class);

        ApiResponse<EmployeeResponse> response = ApiResponse.<EmployeeResponse>builder()
                .status("Employee found successfully")
                .results(employee)
                .build();
        return ResponseEntity.ok(response);
    }


}
