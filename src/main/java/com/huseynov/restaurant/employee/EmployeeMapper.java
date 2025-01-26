package com.huseynov.restaurant.employee;

import com.huseynov.restaurant.employee.data.Employee;
import com.huseynov.restaurant.employee.data.EmployeeDetail;
import com.huseynov.restaurant.employee.view.EmployeeDTO;
import com.huseynov.restaurant.employee.view.EmployeeDetailDTO;
import com.huseynov.restaurant.employee.view.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EmployeeMapper {


    public Employee createEmployeeRequestToEntity(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setSurname(request.getSurname());
        employee.setEmail(request.getEmail());

        EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setAddress(request.getAddress());
        employeeDetail.setPhoneNumber(request.getPhoneNumber());
        employeeDetail.setGender(request.getGender());
        employeeDetail.setSalary(request.getSalary());
        employee.setEmployeeDetail(employeeDetail);
        log.info("EmployeeMapper::convertCreateEmployeeRequestToEmployee employee {}", employee);
        return employee;
    }

    public EmployeeDTO entityToDTO(Employee employee) {
        EmployeeDTO response = new EmployeeDTO();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setSurname(employee.getSurname());
        response.setEmail(employee.getEmail());

        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.setDetailId(employee.getEmployeeDetail().getId());
        employeeDetailDTO.setEnabled(employee.getEmployeeDetail().isEnabled());
        employeeDetailDTO.setAddress(employee.getEmployeeDetail().getAddress());
        employeeDetailDTO.setPhoneNumber(employee.getEmployeeDetail().getPhoneNumber());
        employeeDetailDTO.setSalary(employee.getEmployeeDetail().getSalary());

        response.setEmployeeDetailDTO(employeeDetailDTO);
        log.info("EmployeeMapper::entityToDTO response {}", response);

        return response;
    }

    public EmployeeResponse entityToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setSurname(employee.getSurname());
        response.setEmail(employee.getEmail());
        return response;
    }

}
