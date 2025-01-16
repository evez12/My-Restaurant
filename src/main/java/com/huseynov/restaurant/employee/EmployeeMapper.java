package com.huseynov.restaurant.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class EmployeeMapper {


    public static Employee convertCreateEmployeeRequestToEmployee(CreateEmployeeRequest request) {
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
        log.info("EmployeeMapper::convertCreateEmployeeRequestToEmployee employeeDetail {}", employee.getEmployeeDetail());
        return employee;
    }

    public EmployeeResponse convertEntityToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEmployeeId(employee.getId());
        response.setName(employee.getName());
        response.setSurname(employee.getSurname());
        response.setEmail(employee.getEmail());

        response.setAddress(employee.getEmployeeDetail().getAddress());
        response.setPhoneNumber(employee.getEmployeeDetail().getPhoneNumber());
        response.setSalary(employee.getEmployeeDetail().getSalary());
        response.setGender(employee.getEmployeeDetail().getGender());

        return response;
    }


}
